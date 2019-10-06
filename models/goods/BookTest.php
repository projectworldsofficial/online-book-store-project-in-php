<?php



spl_autoload_register(function ($class){
    $arr=['goods','interfaces','orders','reviews','serve','customer'];
    foreach ($arr as $val) {
        $path=__DIR__."/../$val/$class.php";
        if (file_exists($path))
            require_once $path;
    }
});
class BookTest
{

    public function testBuy()
    {
        $b=new Book(125412,'ahmed kh twfiq',65465465454,70,55,'  ','a book',100,100);
        $b->buy(20);
        echo"the quantity is". $b->getQuantity()."<br/>";
        echo "the actual quantity is ".$b->getActualQuantity();
    }

    public function testAdd()
    {

    }

    public function testDamageAllData()
    {
        $b=new Book(125412,'ahmed kh twfiq',65465465454,70,55,'  ','a book',100,100);
        $c=$b;
        $b->damageAllData();
        echo $c->getId();

    }
}
$test=new BookTest();
$test->testBuy();