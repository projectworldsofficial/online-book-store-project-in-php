<?php


spl_autoload_register(function ($class){
    $arr=['goods','interfaces','orders','reviews','serve'];
    foreach ($arr as $val) {
        $path=__DIR__."/../$val/$class.php";
        if (file_exists($path))
            require_once $path;
    }
});

class Customers
{
    private $name;
    private $address;
    private $email;
    private $phone;
    private $password;

    /**
     * Customers constructor.
     * @param $name
     * @param $address
     * @param $email
     * @param $phone
     * @param $password
     */
    public function __construct($name=null, Address $address=null, $email=null, $phone=null, $password=null)
    {
        $this->name = $name;
        $this->address = $address;
        $this->email = $email;
        $this->phone = $phone;
        $this->password = $password;
    }

    /**
     * @return mixed
     */
    public function getName()
    {
        return $this->name;
    }

    /**
     * @return mixed
     */
    public function getAddress():Address
    {
        return $this->address;
    }

    /**
     * @return mixed
     */
    public function getEmail()
    {
        return $this->email;
    }

    /**
     * @return mixed
     */
    public function getPhone()
    {
        return $this->phone;
    }

    /**
     * @return mixed
     */
    public function getPassword()
    {
        return $this->password;
    }


}