<?php
  session_start();
  $count = 0;
  // connect database
  
  $title = "Index";
  include_once "./template/header.php";
  include_once "./functions/database_functions.php";
  $conn = db_connect();
  $row = select4LatestBook($conn);
?>
      <!-- Example row of columns -->
      <p class="lead text-center text-muted">Latest books</p>
      <div class="row">
        <?php foreach($row as $book) { ?>
      	<div class="col-md-3">
      		<a href="book.php?bookisbn=<?php echo $book['book_isbn']; ?>">
           <img class="img-responsive img-thumbnail" src="./bootstrap/img/<?php echo $book['book_image']; ?>">
          </a>
      	</div>
        <?php } ?>
      </div>
<?php
  if(isset($conn)) {mysqli_close($conn);}
  include_once "./template/footer.php";
?>
