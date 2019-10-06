<?php
/**
 * this bage  coded  by Ahmed  Embaby in  24  SEP  2019
 */
spl_autoload_register(function ($class){
    $arr=['goods','interfaces','orders','reviews','serve','customer'];
    foreach ($arr as $val) {
        $path=__DIR__."/../$val/$class.php";
        if (file_exists($path))
            require_once $path;
    }
});

interface FileInterface
{
    /**
     * @return bool if the file is in temp
     */
    public function isTemp():bool;

    /**
     * @param Condition|null $co
     * @return bool true if it's successfully moved .
     */
    public function move(Condition $co=null):bool;
}