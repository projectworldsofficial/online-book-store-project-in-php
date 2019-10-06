<?php
/**
 * this bage  coded  by Ahmed  Embaby in  24  SEP  2019
 */

/**
 * this page  coded  by Ahmed  Embaby in  29  SEP  2019
 */

class Admin
{
    private $id;
    private $name;
    private $pass;
    private static $admins=[];

    /**
     * Admin constructor.
     * @param $id
     * @param $name
     * @param $pass
     */
    public function __construct ($id, $name, $pass)
    {
        $this->id = $id;
        $this->name = $name;
        $this->pass = $pass;
        Admin::$admins;

    }

    /**
     * @return mixed
     */
    public function getId ()
    {
        return $this->id;
    }

    /**
     * @return mixed
     */
    public function getName ()
    {
        return $this->name;
    }

    /**
     * @return mixed
     */
    public function getPass ()
    {
        return $this->pass;
    }


}