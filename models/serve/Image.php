<?php
/**
 * this bage  coded  by Ahmed  Embaby in  24  SEP  2019
 */

class Image
{
    function upload($newname):bool {
        $acceptable=array('image/jpeg', 'image/png',);
        $MAX_SIZE=2097152;
        $errors=[];
        $GLOBALS['imagePath']=__DIR__.'/../images/'.$newname;
        if ($_FILES['image']['size']>$MAX_SIZE ||$_FILES['image']['size']==0)
            $errors[]='Maxmium allowable size of the image is 2 mega byte or the size of image is zero';
        if (!in_array($_FILES['image']['type'],$acceptable)||$_FILES['image']['type']=='')
            $errors[]='Acceptable images is JPG or PNG';
        if (!count($errors)) {
            return move_uploaded_file($_FILES['image']['tmp_name'], $GLOBALS['imagePath']);
        }
        else {
            foreach ($errors as $er)
                echo "<script>alert(\"$er\")</script>";
        }
        return false;

    }

}