<?php

spl_autoload_register(function ($class){
    $arr=['goods','interfaces','orders','reviews','serve','customer'];
    foreach ($arr as $val) {
        $path=__DIR__."/../$val/$class.php";
        if (file_exists($path))
            require_once $path;
    }
});

class Book implements Damage
{
    private $id;
    private $name;
    private $author;
    private $publisher;
    private $genre;
    private $isbn;
    private $sellPrice;
    private $buyPrice;
    private $image;
    private $description;
    private $actualQuantity;
    private $quantity;
    private static $objects=[];

    /**
     * Book constructor.
     * @param $id
     * @param $author
     * @param $publisher
     * @param $genre
     * @param $isbn
     * @param $sellPrice
     * @param $buyPrice
     * @param $image
     * @param $description
     * @param $actualQuantity
     * @param $quantity
     */
    public function __construct($id, $name,int $isbn,float $sellPrice,float $buyPrice,string $image,string $description,int $actualQuantity,int $quantity,Author $author=NULL,Publishers $publisher=NULL, Genre $genre=NULL)
    {
        $this->id = $id;
        $this->name=$name;
        $this->author = $author;
        $this->publisher = $publisher;
        $this->genre = $genre;
        $this->isbn = $isbn;
        $this->sellPrice = $sellPrice;
        $this->buyPrice = $buyPrice;
        $this->image = $image;
        $this->description = $description;
        $this->actualQuantity = $actualQuantity;
        $this->quantity = $quantity;
        Book::$objects[$this->id]=$this;
    }


    public function buy($quantity=1):bool{
        if ($this->quantity>=$quantity) {
            $this->quantity -= $quantity;
            return true;
        }
        return false;
    }

    public function add($quantity){
         $this->quantity+=$quantity;
         $this->actualQuantity+=$quantity;
    }

    public function backBook ($amount){
        $this->quantity+=$amount;
    }

    public function delivering_done($amount){
        $this->actualQuantity-=$amount;
    }

    public function damageAllData()
    {
        $this->id=NULL;
        $this->name=NULL;
        $this->quantity=NULL;
        $this->author=NULL;
        $this->isbn=NULL;
        $this->buyPrice=NULL;
        $this->sellPrice=NULL;
        $this->image=NULL;
        $this->description=NULL;
        $this->actualQuantity=NULL;
        $this->genre=NULL;
    }

    /**
     * @return mixed
     */
    public function getId():int
    {
        return $this->id;
    }

    /**
     * @return string
     */
    public function getName(): string
    {
        return $this->name;
    }

    /**
     * @return Author
     */
    public function getAuthor(): Author
    {
        return $this->author;
    }

    /**
     * @return publisher
     */
    public function getPublisher(): Publishers
    {
        return $this->publisher;
    }

    /**
     * @return Genre
     */
    public function getGenre(): Genre
    {
        return $this->genre;
    }

    /**
     * @return int
     */
    public function getIsbn(): int
    {
        return $this->isbn;
    }

    /**
     * @return float
     */
    public function getSellPrice(): float
    {
        return $this->sellPrice;
    }

    /**
     * @return float
     */
    public function getBuyPrice(): float
    {
        return $this->buyPrice;
    }

    /**
     * @return string
     */
    public function getImage(): string
    {
        return $this->image;
    }

    /**
     * @return string
     */
    public function getDescription(): string
    {
        return $this->description;
    }

    /**
     * @return int
     */
    public function getActualQuantity(): int
    {
        return $this->actualQuantity;
    }

    /**
     * @return int
     */
    public function getQuantity(): int
    {
        return $this->quantity;
    }

    /**
     * @return array
     */
    public static function getObjects(): array
    {
        return self::$objects;
    }



}