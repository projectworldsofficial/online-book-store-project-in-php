<?php


spl_autoload_register(function ($class){
    $arr=['models/goods',
        'models/orders',
        'models/reviews',
        'models/customer',
        'models/admin'];

    foreach ($arr as $val) {
        $path=__DIR__."/../../$val/$class.php";
        if (file_exists($path))
            require_once $path;
    }
});
class Talking
{
    protected $conn;
    protected $stat;
    private static $userName = 'root';
    private static $pass = '';
    private static $dbname = 'book_store';
    private static $host = 'localhost';
    public const   ORDER_BY_NAME_A_Z = 1;
    public const   ORDER_BY_NAME_Z_A = 2;
    public const   ORDER_BY_PRICE = 3;
    public const   ORDER_BY_SELLING = 4;//common books (best sellers)
    public const   SEARCH_BOOK_NAME = 5;
    public const   SEARCH_AUTHOR_NAME = 6;
    public const   SEARCH_BOOK_CATEGORY = 7;

    /**
     * Talking constructor.
     */
    public function __construct ()
    {
        $options = [
            PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
            PDO::ATTR_CASE => PDO::CASE_NATURAL,
            PDO::ATTR_ORACLE_NULLS => PDO::NULL_EMPTY_STRING,
            PDO::MYSQL_ATTR_INIT_COMMAND => 'set names utf8',
            PDO::ATTR_PERSISTENT
        ];
        try {
            $this->conn = new PDO('mysql:host=' . Talking::$host . ';dbname=' . Talking::$dbname, Talking::$userName, Talking::$pass, $options);
        } catch (PDOException $exception) {
            echo $exception->getMessage();
            die();
        }
    }

    public static function prepareClass (string $user, string $pass, string $dbname, string $host)
    {
        Talking::$pass = $pass;
        Talking::$userName = $user;
        Talking::$host = $host;
        Talking::$dbname = $dbname;
    }

    /**
     * @param int $offset
     * @param $limit
     * @return Book
     */
    public function getAvailableBooks ()
    {
        try {
            $this->stat = $this->conn->prepare("
             SELECT a.name as author, b.book_id,b.title,b.sell_price,b.ISBN,b.buy_price,p.name as publisher_name,p.publisher_id,b.quantity,b.actual_quantity,b.description,b.image
             FROM author a JOIN book b ON a.author_id=b.author_id 
                 JOIN publisher p on b.publisher_id = p.publisher_id
             WHERE quantity>0 ");
            $this->stat->execute();
            while ($row = $this->stat->fetch(PDO::FETCH_ASSOC)) {
                $publisher = new Publishers($row['publisher_name'], $row['publisher_id']);
                new Book(
                    $row['book_id'],
                    $row['title'],
                    $row['ISBN'],
                    $row['sell_price'],
                    $row['buy_price'],
                    $row['image'],
                    $row['description'],
                    $row['actual_quantity'],
                    $row['quantity'],
                    new Author($row['author']),
                    $publisher,
                );
            }

        } catch (PDOException $exception) {

        }
    }

    public function bestSeller ($limt = 50): PDOStatement
    {
        try {
            $this->stat = $this->conn->prepare("
            SELECT b.book_id,b.title,b.buy_price,b.image, a.name AS author
            FROM book b JOIN order_details od on b.book_id = od.book_id
            JOIN author a on b.author_id = a.author_id,orders o
            where o.order_date<TIMESTAMPADD(YEAR ,1,o.order_date)
            AND b.quantity>0
            GROUP by od.book_id
            ORDER BY COUNT(od.book_id) DESC LIMIT ?;");
            $this->stat->bindValue(1, $limt, PDO::PARAM_INT);
            return $this->stat;
        } catch (PDOException $exception) {
            echo $exception->getMessage();
            die();
        }
    }

    public function getCategory (): PDOStatement
    {
        try {
            $this->stat = $this->conn->prepare("
SELECT genre.genre_desc 
FROM genre;");
            $this->stat->execute();
            return $this->stat;
        } catch (PDOException $exception) {
            echo $exception->getMessage();
            die();
        }
    }

    public function selectALL (int $orderBy = 1)
    {
        try {
            $sql = "SELECT b.book_id,b.title,b.buy_price,b.image, a.name AS author
              FROM book b JOIN author a on b.author_id = a.author_id
              WHERE b.quantity>0 ";
            switch ($orderBy) {
                case 1:
                    $sql .= ' ORDER BY 2 ;';
                    break;
                case 2:
                    $sql .= ' ORDER BY 2 DESC;';
                    break;
                case 4:
                    $sql = "
SELECT b.book_id,b.title,b.buy_price,b.image, a.name AS author,COUNT(b.book_id)
FROM book b JOIN order_details od on b.book_id = od.book_id
JOIN author a on b.author_id = a.author_id,orders o
WHERE b.quantity>0
AND o.order_date<TIMESTAMPADD(YEAR ,1,o.order_date)
GROUP BY b.book_id,b.title
ORDER BY COUNT(b.book_id) DESC,2;";
                    break;
                default:
                    $sql .= ' ORDER BY 3,2;';
            }
            $this->stat = $this->conn->prepare($sql);
            $this->stat->execute();
            return $this->stat;
        } catch (PDOException $exception) {
            echo $exception->getMessage();
            die();
        }
    }

    public function getAuthorworks ($likeAuthor)
    {
        $likeAuthor = "%$likeAuthor%";
        try {
            $this->stat = $this->conn->prepare("
SELECT b.book_id,b.title,b.buy_price,b.image, a.name AS author
FROM book b JOIN author a on b.author_id = a.author_id
WHERE a.name like ? AND  b.quantity>0;");
            $this->stat->bindParam(1, $likeAuthor);
            $this->stat->execute();
            return $this->stat;
        } catch (PDOException $exception) {
            echo $exception->getMessage();
            die();
        }
    }

    public function getBookInfo ($id): Book
    {
        try {
            $this->stat = $this->conn->prepare("
SELECT b.book_id,b.title,b.ISBN,b.sell_price,b.buy_price,b.image,b.quantity,b.actual_quantity,b.description,a.name as author,p.name as publisher_name
FROM book b,author a,publisher p
WHERE b.book_id=?
AND b.author_id=a.author_id
AND p.publisher_id=b.publisher_id;
");
            $this->stat->bindParam(1, $id);
            $this->stat->execute();
            $row = $this->stat->fetch(PDO::FETCH_ASSOC);
            return new Book(
                $id,
                $row['title'],
                $row['ISBN'],
                $row['sell_price'],
                $row['buy_price'],
                $row['image'],
                $row['description'],
                $row['actual_quantity'],
                $row['quantity'],
                new Author($row['author'], ''),
                new Publishers($row['publisher_name'])
            );
        } catch (PDOException $exception) {
            echo $exception->getMessage();
            die();
        }
    }

    public function getNotAvailableBooks ()
    {
        try {
            $this->stat = $this->conn->prepare("
SELECT b.book_id,b.title,b.sell_price,b.buy_price,b.description,b.actual_quantity,p.phone as publisher,p.phone as publisher_phone,sum(od.quantity) as co
from book b,order_details od,orders o,publisher p
where o.order_date <= TIMESTAMPADD(YEAR ,1,o.order_date)
AND b.book_id=od.book_id
AND b.quantity=0
AND p.publisher_id=b.publisher_id
AND od.order_id=o.order_id
GROUP BY  b.book_id
ORDER BY sum(od.quantity) DESC ;");
            $this->stat->execute();
            return $this->stat;
        } catch (PDOException $exception) {
            echo $exception->getMessage();
            die();
        }
    }

    public function getBooksOffPublisher (int $pubid): PDOStatement
    {
        try {
            $this->stat = $this->conn->prepare("
            SELECT b.book_id,b.title,b.image,b.sell_price,b.buy_price,b.ISBN
            FROM publisher p JOIN book b on p.publisher_id = b.publisher_id
            WHERE p.publisher_id=? AND b.quantity>0;");
            $this->stat->bindParam(1, $pubid);
            $this->stat->execute();
            return $this->stat;
        } catch (PDOException $PDOException) {
            echo $PDOException->getMessage();
            die();
        }
    }

    public function getEarnings ($from, $to)
    {
        try {
            $this->stat = $this->conn->prepare(" SELECT SUM(b.quantity*(b.buy_price-b.sell_price)) AS total
FROM book b JOIN order_details od on b.book_id = od.book_id
JOIN orders o on od.order_id = o.order_id
WHERE o.order_date IS NOT NULL
AND timestamp(o.order_date) >= ? and timestamp(o.order_date)<=?;");
            $this->stat->bindValue(1, $from);
            $this->stat->bindValue(2, $to);
            $this->stat->bindColumn(1, $val);
            $this->stat->execute();
            $this->stat->fetch(PDO::FETCH_BOUND);
            return $val;
        } catch (PDOException $exception) {
            echo $exception->getMessage();
            die();
        }
    }

    public function Getorders ()
    {
        $this->stat = $this->conn->prepare("
        SELECT  orders.order_date from orders;
");
        $this->stat->execute();
        return $this->stat;
    }

    /**
     * @param int max $limit od row he want and it's default =20.
     * @return bool|PDOStatement: false if there no data in book table in DB,
     * else i wll return PDOStatement object not an object.
     * it may be big amount of data ,so it is not a good idea to cash it
     */
    public function lastUpload (int $limit = 20)
    {
        try {
            $this->stat = $this->conn->prepare("
            SELECT b.book_id,b.title,b.sell_price,b.image,a.name AS author
            FROM book b JOIN author a on b.author_id = a.author_id
            WHERE b.quantity>0
            ORDER BY b.buying_date DESC limit ?;");
            $this->stat->bindParam(1, $limit, 1);
            $this->stat->execute();
            return $this->stat;
        } catch (PDOException $exception) {
            echo $exception->getMessage();
            die();
        }
    }

    /**
     * @param $id : of the customer .
     * @return Customers or null if there are no any result from Db.
     */

    public function getCustomerByid ($id): Customers
    {
        try {
            $this->stat = $this->conn->prepare("
          SELECT c.name,c.address_id,c.phone,c.email,c.password FROM customer c WHERE customer_id=?;
        ");
            $this->stat->execute([0 => $id]);
            $row = $this->stat->fetch(PDO::FETCH_ASSOC);
            $address = $this->getAddress($row['address_id']);
            return new Customers($row['name'], $address, $row['email'], $row['phone'], $row['password']);
        } catch (PDOException $exception) {
            echo $exception->getMessage();
            return null;
        }
    }

    /**
     * @param $orderId
     * @return Customers the owner of this order hold in object
     */
    public function getCustomerByorder ($orderId): Customers
    {
        try {
            $this->stat = $this->conn->prepare("
            SELECT c.name,c.address_id,c.phone,c.email,c.password FROM customer c WHERE customer_id=(
                SELECT o.customer_id FROM orders o WHERE o.order_id=?
            );
        ");
            $this->stat->execute([0 => $orderId]);
            $row = $this->stat->fetch(PDO::FETCH_ASSOC);
            $address = $this->getAddress($row['address_id']);
            return new Customers($row['name'], $address, $row['email'], $row['phone'], $row['password']);
        } catch (PDOException $exception) {
            echo $exception->getMessage();
            return null;
        }
    }

    /**
     * @param $id int .
     * @return Address object hold the data about the address of passed id.
     */

    public function getAddress ($id): Address
    {
        try {
            $this->stat = $this->conn->prepare("
            SELECT  country, city, street, build_no FROM address a WHERE address_id=?
            ");
            $this->stat->bindValue(1, $id, PDO::PARAM_INT);
            $row = $this->stat->fetch();
            return new Address($row['country'], $row['city'], $row['street'], $row['build_no']);
        } catch (PDOException $exception) {
            echo $exception->getMessage();
            return null;
        }
    }


    public function seeYou ()
    {
        $this->conn = null;
        $this->stat = null;
    }

    /**
     * @param $customweId
     * @return Orders .
     * get an object of Orders that hold all data about order of the customer and it's orderDetails .
     */

    public function getOrderofCustomer ($customerId): Orders
    {
        try {
            $this->stat = $this->conn->prepare("
            SELECT book.book_id, book.title,book.ISBN,book.sell_price,book.buy_price,book.image,book.actual_quantity,book.quantity,a.name as author,od.quantity as orderQuantity
            FROM customer join orders o on customer.customer_id = o.customer_id
                join order_details od on o.order_id = od.order_id
                JOIN book on od.book_id = book.book_id
                JOIN author a on book.author_id = a.author_id
            WHERE customer.customer_id=?
            ");
            $this->stat->bindValue(1, $customerId, PDO::PARAM_INT);
            $this->stat->execute();
            $order = new Orders();
            while ($row = $this->stat->fetch(PDO::FETCH_ASSOC)) {
                $b = new Book(
                    $row['book_id'], $row['title'],
                    $row['ISBN'], $row['sell_price'],
                    $row['buy_price'], $row['image'],
                    '', $row['actual_quantity'],
                    $row['quantity'], new Author($row['author']));
                $order->addOrderDetails($b, $row['orderQuantity']);
            }
            return $order;
        } catch (PDOException $exception) {
            echo $exception->getMessage();
            return null;
        }
    }

    /**
     * @param int $search by what ?
     * @param string $str
     * if $search = $this->SEARCH_AUTHOR_NAME (6)it will search by author by "like" method .
     * if $search = $this->SEARCH_BOOK_CATEGORY(7) it will search by genre (it should be  the same as the name in database to get that data )
     * in other words it uses "=" unlike the above($this->SEARCH_AUTHOR_NAME,$this->SEARCH_BOOK_CATEGORY)
     * and others .if $search $this->SEARCH_BOOK_NAME (5)it will search by name(title) of the book  by "like" method.
     * if its not 6 and 7 it consider that it's 5
     * @return void and it cash the data in the shape of Book objects.
     * you can only get result data from Book::getObjects():array
     */

    public function cashBooks (int $search, string $str): void
    {
        if ($search == 7) {
            $query = "SELECT b.*,a.name AS author
                    FROM genre JOIN book b 
                    ON genre.genre_id = b.genre_id JOIN author a on b.author_id = a.author_id
                    WHERE genre_desc=?;";

        } else {
            $type = '';
            switch ($search) {
                case 6:
                    $type = 'a.name';
                    $str = "%$str%";
                    break;
                default:
                    $type = 'b.title';
                    $str = "%$str%";
                    break;
            }
            $query = "SELECT b.*,a.name AS author
                      FROM book b JOIN author a on b.author_id = a.author_id WHERE $type like ?;";
        }
        try {
            $this->stat = $this->conn->prepare($query);
            echo $query;
            $this->stat->bindValue(1, $str);
            $this->stat->execute();
            while ($row = $this->stat->fetch(PDO::FETCH_ASSOC))
                new Book(
                    $row['book_id'], $row['title'],
                    $row['ISBN'], $row['sell_price'],
                    $row['buy_price'], $row['image'],
                    $row['description'], $row['actual_quantity'],
                    $row['quantity'], new Author($row['author']));
        } catch (PDOException $PDOException) {
            echo $PDOException->getMessage();
        }
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////
/// they do not test

    public function getAllpublishers (): PDOStatement
    {
        try {
            $this->stat = $this->conn->prepare("
            SELECT publisher_id,name FROM publisher ORDER BY  2 ");
            $this->stat->execute();
            return $this->stat;
        } catch (PDOException $exception) {
            echo $exception->getMessage();
            return NULL;
        }
    }

    public function getPublisher ($id): Publishers
    {
        try {
            $this->stat = $this->conn->prepare("
            SELECT publisher_id,name FROM publisher where publisher_id=?;");
            $this->stat->bindValue(1, $id, PDO::PARAM_INT);
            $this->stat->execute();
            $row = $this->stat->fetch(PDO::FETCH_ASSOC);
            return new Publishers($row['name'], $row['publisher_id']);
        } catch (PDOException $exception) {
            echo $exception->getMessage();
            return NULL;
        }
    }

    public function getPublisherbyname ($name): Publishers
    {
        try {
            $this->stat = $this->conn->prepare("
            SELECT publisher_id,name FROM publisher where name=?;");
            $this->stat->bindValue(1, $name);
            $this->stat->execute();
            $row = $this->stat->fetch(PDO::FETCH_ASSOC);
            return new Publishers($row['name'], $row['publisher_id']);
        } catch (PDOException $exception) {
            echo $exception->getMessage();
            return NULL;
        }
    }

    /**
     * @param $name : the name of admin
     * @return Admin or null if any exception happened or user not found
     */
    public function getadmin ($name): Admin
    {
        try {
            $this->stat = $this->conn->prepare("
            SELECT * FROM admin WHERE user_name=?;
            ");
            $this->stat->execute([0 => $name]);
            if (!$this->stat->rowCount())
                return null;
            $row = $this->stat->fetch(PDO::FETCH_ASSOC);
            return new Admin($row['admin_id'], $row['user_name'], $row['pass']);
        } catch (Exception $PDOException) {
            echo $PDOException->getMessage();
            return null;
        }
    }

    /**
     * @return PDOStatement
     *
     */
    public function getAllAdmins (): PDOStatement
    {
        try {
            $this->stat = $this->conn->prepare("
            SELECT * FROM admin ORDER BY admin_id;
            ");
            $this->stat->execute();
            return $this->stat;
        } catch (Exception $PDOException) {
            echo $PDOException->getMessage();
        }
    }


    public function getAllAuthors (): PDOStatement
    {
        try {
            $this->stat = $this->conn->prepare("
            SELECT * FROM author  ORDER BY name;
            ");
            $this->stat->execute();
            return $this->stat;
        } catch (Exception $PDOException) {
            echo $PDOException->getMessage();
        }
    }

    public function getAuthor ($name): Author
    {
        try {
            $this->stat = $this->conn->prepare("
            SELECT * FROM author  WHERE name=?;
            ");
            $this->stat->bindValue(1,$name);
            $this->stat->execute();
            $row=$this->stat->fetch();
            return new Author($name,$row['author_id']);
        } catch (Exception $PDOException) {
            echo $PDOException->getMessage();
        }
    }

    public function getGenre ($name): Genre
    {
        try {
            $this->stat = $this->conn->prepare("
            SELECT * FROM genre  WHERE genre_desc=?;
            ");
            $this->stat->bindValue(1,$name);
            $this->stat->execute();
            $row=$this->stat->fetch();
            return new Genre($row['genre_desc'],$row['genre_id']);
        } catch (Exception $PDOException) {
            echo $PDOException->getMessage();
            return null;
        }
    }

    public function getAllGenres (): PDOStatement
    {
        try {
            $this->stat = $this->conn->prepare("
            SELECT * FROM genre  ORDER BY genre_desc;
            ");
            $this->stat->execute();
            return $this->stat;
        } catch (Exception $PDOException) {
            echo $PDOException->getMessage();
            return null;
        }
    }

    protected function deleteImage($image):bool{
        return unlink($image);
    }

}