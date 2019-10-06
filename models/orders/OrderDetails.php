<?php

spl_autoload_register(/**
 * @param $class
 */ function ($class){
    $arr=['goods','interfaces','orders','reviews','serve','customer'];
    foreach ($arr as $val) {
        $path=__DIR__."/../$val/$class.php";
        if (file_exists($path))
            require_once $path;
    }
});

/**
 * Class OrderDetails
 */
class OrderDetails extends Review implements Damage
{
    /**
     * @var
     */
    private $book;
    /**
     * @var
     */
    private $quantity;

    /**
     * OrderDetails constructor.
     * @param $book
     * @param $quantity
     */
    public function __construct ($book, $quantity,$openion=NULL,$reviewDegree=NULL)
    {
        parent::__construct($openion,$reviewDegree);
        $this->book = $book;
        $this->quantity = $quantity;
    }


    /**
     * @return mixed
     */
    public function getBook():Book
    {
        return $this->book;
    }

    /**
     * @return mixed
     */
    public function getQuantity()
    {
        return $this->quantity;
    }

    /**
     * @param mixed $quantity
     */
    public function setQuantity($quantity): void
    {
        $this->quantity = $quantity;
    }

    /**
     * @param int $quantity
     */
    public function addQuantity(int $quantity){
        $this->quantity+=$quantity;
    }

    /**
     * @param mixed $book
     */
    public function setBook($book): void
    {
        $this->book = $book;
    }

    /**
     *
     */
    public function damageAllData()
    {
        unset(
        $this->quantity,
        $this->book
        );
    }
    public function getDetailsPrice(){
        return $this->getBook()->getSellPrice()*$this->quantity;
    }

}