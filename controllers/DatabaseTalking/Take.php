<?php
/**
 * this class is  coded  by Ahmed  Embaby in  24  SEP  2019
 */
spl_autoload_register(function ($class){
    $arr=[__DIR__.'/../../models/goods',
        __DIR__.'/../../models/orders',
        __DIR__.'/../../models/reviews',
        __DIR__.'/../../models/customer',
        './',
    ];
    foreach ($arr as $val) {
        $path="$val/$class.php";
        if (file_exists($path))
            require_once $path;
    }
});


class Take extends Talking
{
    /**
     * @param Orders $order
     * insert all data of the order
     * includes the customer that make the order and his address data
     * also all the orderDetails related to this order.
     * @return false only if any error happened or  no orderDetails had been found in object $order
     */
    public function takeorder (Orders $order,&$orderNumber=null):bool
    {
        try{
            $customer=$order->getCustomer();
            $address=$customer->getAddress();
            $addressId=rand(1, 1000000000);
            $customerId=rand(1, 1000000000);
            $orderId=rand(1, 1000000000);
            $orderNumber=$orderId;
           $this->conn->beginTransaction();
           //insert address of the customer
           $this->stat=$this->conn->prepare('INSERT INTO address VALUES (?,?,?,?,?)');
           $this->stat->execute(array(
               0=> $addressId,
               1=> $address->getCountry(),
               2=>$address->getCity(),
               3=>$address->getStreet(),
               4=>$address->getBuildNo()));
           //insert the customer data
           $this->stat=$this->conn->prepare('INSERT INTO customer (customer_id, name, address_id, email, phone, password) VALUES (?,?,?,?,?,?)');
           $this->stat->execute(array(
               0=>$customerId,
               1=>$customer->getName(),
               2=>$addressId,
               3=>$customer->getEmail(),
               4=>$customer->getPhone(),
               5=>$customer->getPassword()
           ));
           /**
             * insert order data.
             * order_Date DEFAULT is  CURRENT_TIMESTAMP().
             * delivering_date DEFAULT is null.
             *  by NULL value it's possible to know whether the order delivered or not .
             */
           $this->stat=$this->conn->prepare('INSERT INTO orders (order_id, customer_id) values (?,?)');
           $this->stat->execute(array(
               0=>$orderId,
               1=>$customerId
           ));
           /**
            *insert the order details of the order
            * if the getOrderDetails() returns an empty array ,
            * it means that customer that make the order do not order any book .
            * thus if no order Details nothing will be committed
            */
           if (count($order->getOrderDetails())) {
               foreach ($order->getOrderDetails() as $id => $detail) {
                   $this->insertANOrderDetails($orderId,$detail);
                   sleep(.0000001);
               }
           }
           else {
               $this->conn->rollBack();
               return false;
           }
           //no errors or empty OrderDetails then commit
           return $this->conn->commit();
        }catch (PDOException $PDOException){
           $this->conn->rollBack();
           echo $PDOException->getMessage();
           return false;
       }
    }

    private function insertANOrderDetails($orderId,OrderDetails $detail){
        try{
            $this->stat = $this->conn->prepare('INSERT INTO order_details (order_details_id, order_id, book_id,quantity) VALUES (?,?,?,?)');
            $this->stat->execute(array(
                0 => rand(1, 1000000000),
                1 => $orderId,
                2 => $detail->getBook()->getId(),
                3=>$detail->getQuantity()
            ));
            $this->stat=$this->conn->prepare("
            UPDATE book set quantity=(Select quantity from book where book_id=?)-? where book_id=?");
            $this->stat->execute(array(
                    0=>$detail->getBook()->getId(),
                    1=>$detail->getQuantity(),
                    2 =>$detail->getBook()->getId()
                )
            );
        }catch (PDOException $PDOException){
            $this->conn->rollBack();
            echo $PDOException->getMessage();
        }
    }
    private function undoOrder($orderId) {
        try{
            $this->stat=$this->conn->prepare("
            UPDATE  book b SET quantity=(
            SELECT o.quantity
            FROM  order_details o JOIN orders o3 on o.order_id = o3.order_id
            WHERE o3.order_id=? AND b.book_id=o.book_id )+(
            SELECT b.quantity FROM book c WHERE c.book_id=b.book_id
    )
       WHERE b.book_id in (
            SELECT x.book_id
            FROM book x JOIN order_details od on x.book_id = od.book_id
            JOIN orders o2 on od.order_id = o2.order_id
            WHERE o2.order_id=? );
            ");
            $this->stat->execute([0=>$orderId,1=>$orderId]);
            $this->stat=$this->conn->prepare("
            DELETE FROM order_details WHERE order_id=?
            ");
            $this->stat->execute([0=>$orderId]);
        }catch (PDOException $exception){
            $this->conn->rollBack();
        }
    }

    public function updateOrder($orderId,Orders $order):bool {
        try{
            $this->conn->beginTransaction();
            $this->undoOrder($orderId);
            foreach ($order->getOrderDetails() as $detail)
                $this->insertANOrderDetails($orderId,$detail);
            $this->conn->commit();
            return true;
        }catch (PDOException $PDOException){
            $this->conn->rollBack();
            echo $PDOException->getMessage();
            return false;
        }

    }


    public function updateCustomer($orderId,Customers $customer){

    }

    /**
     * @param Book $book
     * insert the book into DB .
     * @return true if the the book inserted successfully else it return false.
     */

    public function takeBook(Book $book):bool {
        try{
            $this->stat=$this->conn->prepare("
            insert into book(book_id, title, author_id, publisher_id, genre_id, ISBN, sell_price, buy_price, image, description, quantity, actual_quantity) 
            values (?,?,?,?,?,?,?,?,?,?,?,?);");
            $this->stat->bindValue(1,$book->getId(),PDO::PARAM_INT);
            $this->stat->bindValue(2,$book->getName());
            $this->stat->bindValue(3,$book->getAuthor()->getId(),PDO::PARAM_INT);
            $this->stat->bindValue(4,$book->getPublisher()->getId(),PDO::PARAM_INT);
            $this->stat->bindValue(5,$book->getGenre()->getId(),PDO::PARAM_INT);
            $this->stat->bindValue(6,$book->getIsbn(),PDO::PARAM_INT);
            $this->stat->bindValue(7,$book->getSellPrice());
            $this->stat->bindValue(8,$book->getBuyPrice());
            $this->stat->bindValue(9,$book->getImage());
            $this->stat->bindValue(10,$book->getDescription());
            $this->stat->bindValue(11,$book->getQuantity(),PDO::PARAM_INT);
            $this->stat->bindValue(12,$book->getActualQuantity(),PDO::PARAM_INT);
            $this->stat->execute();
            return true;
        }catch (PDOException $PDOException){
           echo $PDOException->getMessage();
           return false;
        }

    }

    /**
     * @param Book $book
     * @return bool
     *update book without updating the image but it delete the old image
     */

    public function updateBook(Book $book):bool{
        try{

            $this->stat=$this->conn->prepare("SELECT image FROM book WHERE book_id=?");
            $this->stat->execute([0=>$book->getId()]);
            $row=$this->stat->fetch(PDO::FETCH_ASSOC);
            //delete the old image
            $this->deleteImage(__DIR__.'/../../images/'.$row['image']);
            $this->stat=$this->conn->prepare("
            UPDATE book SET author_id=?,publisher_id=?,buy_price=?,sell_price=?,quantity=?,
            actual_quantity=?,image=? WHERE book_id=?;  
            ");
            $this->stat->bindValue(1,$book->getAuthor()->getId(),PDO::PARAM_INT);
            $this->stat->bindValue(2,$book->getPublisher()->getId(),PDO::PARAM_INT);
            $this->stat->bindValue(3,$book->getBuyPrice());
            $this->stat->bindValue(4,$book->getSellPrice());
            $this->stat->bindValue(5,$book->getQuantity(),PDO::PARAM_INT);
            $this->stat->bindValue(6,$book->getActualQuantity(),PDO::PARAM_INT);
            $this->stat->bindValue(7,$book->getImage(),PDO::PARAM_INT);
            $this->stat->bindValue(8,$book->getId(),PDO::PARAM_INT);

            return $this->stat->execute();
        }catch(PDOException $exception){
            echo $exception->getMessage();
            return false;
        }

    }

}