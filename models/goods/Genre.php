<?php



spl_autoload_register(function ($class){
    $arr=['goods','interfaces','orders','reviews','serve','customer'];
    foreach ($arr as $val) {
        $path=__DIR__."/../$val/$class.php";
        if (file_exists($path))
            require_once $path;
    }
});
class Genre
{
    private $description;
    private $id;

    /**
     * Genre constructor.
     * @param $description
     */
    public function __construct($description,$id=null)
    {
        $this->description = $description;
        $this->id=$id;
    }

    /**
     * @return mixed
     */
    public function getDescription ()
    {
        return $this->description;
    }

    /**
     * @return null
     */
    public function getId ()
    {
        return $this->id;
    }




}