-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Oct 06, 2019 at 10:52 PM
-- Server version: 10.4.6-MariaDB
-- PHP Version: 7.3.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `book_store`
--

-- --------------------------------------------------------

--
-- Table structure for table `address`
--

CREATE TABLE `address` (
  `address_id` int(11) NOT NULL,
  `country` varchar(50) NOT NULL,
  `city` varchar(50) NOT NULL,
  `street` varchar(50) NOT NULL,
  `build_no` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `address`
--

INSERT INTO `address` (`address_id`, `country`, `city`, `street`, `build_no`) VALUES
(1, 'Egypt', 'nasr city', 'moharam bek', 12245),
(2, 'Egypt', 'sohag', 'dar elsalam', 55),
(3, 'Egypt', 'sohag', 'dar elsalam', 45),
(4, 'USA', 'LAS FIGAS', 'dezney', 64),
(5, 'algeria', 'woo', 'hamza ben ali', 85),
(1264, 'ddddddd', 'dddddddd', 'ddddddd', 56),
(4204694, 'naga', 'dezni', 'fald', 555),
(5202922, 'Australia', 'dezni', 'fald', 555),
(6611123, 'Australia', 'dezni', 'fald', 555),
(23442805, ' andonisia', 'المحيط', 'fald', 555),
(30493813, 'c', 'c', 'c', 5),
(39875582, 'naga', 'dezni', 'fald', 555),
(46575766, 'naga', 'dezni', 'fald', 555),
(49592353, 'naga', 'dezni', 'fald', 555),
(64952997, 'naga', 'dezni', 'fald', 555),
(106786516, 'naga', 'dezni', 'fald', 555),
(157476154, ' andonisia', 'المحيط', 'fald', 555),
(221790011, 'naga', 'dezni', 'fald', 555),
(227232961, 'djk', 'dh', 'dajk', 6),
(259120209, 'dd', 'd', 'da', 5),
(260530632, 'egypt', 'dd', 'd', 6),
(305051472, ' andonisia', 'المحيط', 'fald', 555),
(307077795, 'naga', 'dezni', 'fald', 555),
(313821255, 'naga', 'dezni', 'fald', 555),
(340763664, ' andonisia', 'المحيط', 'fald', 555),
(345193932, 'egypt', 'tahta', 'elgazar', 5),
(397916956, 'naga', 'dezni', 'fald', 555),
(399311439, 'd', 'd', 'd', 4),
(432638077, 'naga', 'dezni', 'fald', 555),
(436301577, 'egypt', 'tahta', 'algazar', 55),
(443329035, ' andonisia', 'المحيط', 'fald', 555),
(451412955, 'egypt', 'tahta', 'elgazar', 5),
(463511883, 'egypt', 'tahta', 'elgazar', 5),
(475762644, 'dajv', 'dvv', 'vv', 5),
(502812584, 'q', 'd', 'd', 55),
(504139442, 'ek', 'r', 'd', 5),
(508215761, 'ek', 'r', 'd', 5),
(561497198, ' saudan', 'المحيط', 'fald', 555),
(581452607, ' saudan', 'المحيط', 'fald', 555),
(582863521, ' saudan', 'المحيط', 'fald', 555),
(586089845, 'naga', 'dezni', 'fald', 555),
(623637580, 'naga', 'dezni', 'fald', 555),
(632065941, ' andonisia', 'المحيط', 'fald', 555),
(656821364, 'dd', 'd', 'da', 5),
(676041301, '6', '98', '8', 9),
(749385312, 'c', 'c', 'c', 5),
(753969886, 'كوريا الشمالية', 'المحيط', 'fald', 555),
(772748498, 'd', 'd', 'd', 4),
(820918713, 'djk', 'dh', 'dajk', 6),
(823488585, ' saudan', 'المحيط', 'fald', 555),
(897470346, 'naga', 'dezni', 'fald', 555),
(955302274, 'naga', 'dezni', 'fald', 555),
(964399585, 'naga', 'dezni', 'fald', 555),
(966073326, 'كوريا الشمالية', 'المحيط', 'fald', 555),
(975348422, 'ddd', 'fdd', 'ddd', 8);

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `admin_id` int(11) NOT NULL,
  `user_name` varchar(100) NOT NULL,
  `pass` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`admin_id`, `user_name`, `pass`) VALUES
(1, 'admin', '1973');

-- --------------------------------------------------------

--
-- Table structure for table `author`
--

CREATE TABLE `author` (
  `author_id` int(11) NOT NULL,
  `name` varchar(70) NOT NULL,
  `country` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `author`
--

INSERT INTO `author` (`author_id`, `name`, `country`) VALUES
(1, 'nour', 'Egypt'),
(2, 'احمد خالد توفيق', 'libyan'),
(3, 'حنان سعيد', 'quiwait'),
(4, 'اية ابو النجي ', 'iraq');

-- --------------------------------------------------------

--
-- Table structure for table `book`
--

CREATE TABLE `book` (
  `book_id` int(11) NOT NULL,
  `title` varchar(150) NOT NULL,
  `author_id` int(11) DEFAULT NULL,
  `publisher_id` int(11) DEFAULT NULL,
  `genre_id` int(11) DEFAULT NULL,
  `ISBN` int(13) NOT NULL,
  `sell_price` decimal(8,3) NOT NULL,
  `buy_price` decimal(8,3) NOT NULL,
  `image` varchar(100) NOT NULL,
  `description` text DEFAULT NULL,
  `quantity` int(11) NOT NULL,
  `actual_quantity` int(11) NOT NULL,
  `buying_date` date DEFAULT curdate()
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `book`
--

INSERT INTO `book` (`book_id`, `title`, `author_id`, `publisher_id`, `genre_id`, `ISBN`, `sell_price`, `buy_price`, `image`, `description`, `quantity`, `actual_quantity`, `buying_date`) VALUES
(1, 'العزاب', NULL, NULL, 2, 6485654, '200.000', '220.000', '612423294', 'book for learning how to think in programmatic way \n        before reading you should be aware that you know java script \n        a little bit abd have good way to write a good code \n        .i think it is good to solve in hackerrank as it will make you write an\n        efficient good code please try tp practice what you learn \n         ', 2000, 2000, '2019-09-23'),
(2, 'يوتبيا', 4, 4, 4, 56465, '180.000', '190.000', '612423294', 'book for learning how to think in programmatic way \n        before reading you should be aware that you know java script \n        a little bit abd have good way to write a good code \n        .i think it is good to solve in hackerrank as it will make you write an\n        efficient good code please try tp practice what you learn \n         ', 1000, 1000, '2019-09-23'),
(3, 'اكتشف جوزي في الاتوبيس', NULL, NULL, 3, 6565, '1100.000', '1000.000', '612423294', 'book for learning how to think in programmatic way \n        before reading you should be aware that you know java script \n        a little bit abd have good way to write a good code \n        .i think it is good to solve in hackerrank as it will make you write an\n        efficient good code please try tp practice what you learn \n         ', 2000, 2000, '2019-09-23'),
(4, 'خالد بن الوليد', 3, 1, 3, 56898, '500.000', '520.000', '612423294', 'book for learning how to think in programmatic way \n        before reading you should be aware that you know java script \n        a little bit abd have good way to write a good code \n        .i think it is good to solve in hackerrank as it will make you write an\n        efficient good code please try tp practice what you learn \n         ', 1000, 1000, '2019-09-23'),
(5, 'عبقريات عمر', 2, 2, 3, 654165, '550.000', '500.000', '612423294', 'book for learning how to think in programmatic way \n        before reading you should be aware that you know java script \n        a little bit abd have good way to write a good code \n        .i think it is good to solve in hackerrank as it will make you write an\n        efficient good code please try tp practice what you learn \n         ', 1200, 1200, '2019-09-23'),
(6, 'حبيبي داعشي', 3, 5, 4, 56, '75.000', '82.000', '612423294', 'book for learning how to think in programmatic way \n        before reading you should be aware that you know java script \n        a little bit abd have good way to write a good code \n        .i think it is good to solve in hackerrank as it will make you write an\n        efficient good code please try tp practice what you learn \n         ', 1000, 1000, '2019-09-23'),
(7, 'احببت وغدة', 2, 3, 4, 8545654, '300.000', '350.000', '612423294', 'book for learning how to think in programmatic way \n        before reading you should be aware that you know java script \n        a little bit abd have good way to write a good code \n        .i think it is good to solve in hackerrank as it will make you write an\n        efficient good code please try tp practice what you learn \n         ', 1000, 1000, '2019-09-23'),
(8, 'النمر والانثي', 3, 5, 1, 56, '200.000', '250.000', '612423294', 'book for learning how to think in programmatic way \n        before reading you should be aware that you know java script \n        a little bit abd have good way to write a good code \n        .i think it is good to solve in hackerrank as it will make you write an\n        efficient good code please try tp practice what you learn \n         ', 1000, 1000, '2019-09-23'),
(9, 'عايم في الغدر', 4, 2, 4, 654, '120.000', '150.000', '612423294', 'book for learning how to think in programmatic way \n        before reading you should be aware that you know java script \n        a little bit abd have good way to write a good code \n        .i think it is good to solve in hackerrank as it will make you write an\n        efficient good code please try tp practice what you learn \n         ', 993, 1000, '2019-09-23'),
(10, 'اولي اجنجة', NULL, NULL, 2, 561465, '250.000', '200.000', '612423294', 'book for learning how to think in programmatic way \n        before reading you should be aware that you know java script \n        a little bit abd have good way to write a good code \n        .i think it is good to solve in hackerrank as it will make you write an\n        efficient good code please try tp practice what you learn \n         ', 1991, 2000, '2019-09-23'),
(11, 'دعاء الكروان', NULL, NULL, 2, 541, '150.000', '100.000', '612423294', 'book for learning how to think in programmatic way \n        before reading you should be aware that you know java script \n        a little bit abd have good way to write a good code \n        .i think it is good to solve in hackerrank as it will make you write an\n        efficient good code please try tp practice what you learn \n         ', 1499, 1500, '2019-09-23'),
(12, 'الوغد', 3, 3, 4, 51, '200.000', '170.000', '612423294', 'book for learning how to think in programmatic way \n        before reading you should be aware that you know java script \n        a little bit abd have good way to write a good code \n        .i think it is good to solve in hackerrank as it will make you write an\n        efficient good code please try tp practice what you learn \n         ', 2000, 2000, '2019-09-23'),
(13, 'save Queen', 3, 5, 5, 6514, '33.000', '50.000', '612423294', 'book for learning how to think in programmatic way \n        before reading you should be aware that you know java script \n        a little bit abd have good way to write a good code \n        .i think it is good to solve in hackerrank as it will make you write an\n        efficient good code please try tp practice what you learn \n         ', 994, 1000, '2019-09-23'),
(14, 'علي بن ابي طالب ', 4, 3, 3, 545, '40.000', '50.000', '612423294', 'book for learning how to think in programmatic way \n        before reading you should be aware that you know java script \n        a little bit abd have good way to write a good code \n        .i think it is good to solve in hackerrank as it will make you write an\n        efficient good code please try tp practice what you learn \n         ', 1000, 1000, '2019-09-23'),
(15, 'امانوس ', 2, NULL, 5, 656, '120.000', '100.000', '612423294', 'book for learning how to think in programmatic way \n        before reading you should be aware that you know java script \n        a little bit abd have good way to write a good code \n        .i think it is good to solve in hackerrank as it will make you write an\n        efficient good code please try tp practice what you learn \n         ', 1500, 1500, '2019-09-23'),
(51521, 'ss', NULL, NULL, NULL, 51521, '315.000', '120.000', '612423294', 'sd', 1000, 1000, '2019-10-03'),
(47445909, 'dk', NULL, NULL, NULL, 5141, '150.000', '120.000', '612423294', 'dk', 500, 500, '2019-10-03'),
(62277597, 'resvielt evil4', 1, 1, 1, 256566, '190.000', '170.000', '612423294', 'vvv', 1976, 2000, '2019-10-01'),
(232533558, 'resvielt evil4', NULL, NULL, 1, 256566, '600.000', '500.000', '612423294', 'vvv', 3000, 3000, '2019-10-01'),
(329566102, 'tok', 1, 1, 1, 256566, '1000.000', '1100.000', '2', 'da                  jjdaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 2000, 2000, '2019-10-03'),
(509294829, 'resvielt evil4', 1, 1, 1, 256566, '190.000', '170.000', '612423294', 'vvv', 2000, 2000, '2019-10-01');

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `customer_id` int(11) NOT NULL,
  `name` varchar(70) NOT NULL,
  `address_id` int(11) DEFAULT NULL,
  `email` varchar(25) NOT NULL,
  `phone` varchar(15) NOT NULL,
  `password` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`customer_id`, `name`, `address_id`, `email`, `phone`, `password`) VALUES
(1, 'احمد امبابي', 3, 'بب', '01011051728', '1465464بيثضب46'),
(2, 'نورهان محمد', 4, 'صثقصثتة', '564654', 'لثصتنىت666'),
(3, 'عبدو محمد', 2, 'ثصقتن', '654654', 'ثةاتى ثتى'),
(4, 'خالد محسن', 1, '153521يؤريصببثهىصض', 'ثمن', 'ثضصب84بث'),
(5, 'محمد محمود', 4, '546465ص4', 'يسبن', 'يتيت'),
(6, 'علاء السقا', 5, 'يتنؤ', '+يثض5665', 'يت '),
(7, 'محمد متولي ', 3, 'ثيتى', 'ةثيخمةثضخؤمة', 'ص59'),
(8, 'اسعد عبد الشافي ', 5, 'ثةمنبؤم', '498ي4ؤ86', 'يثؤم56'),
(9, 'محمود سعد', 5, 'ينشم', 'يت', 'ينتةي'),
(10, 'خالد مراد', 5, 'يشنة', 'يشة', 'ستئ'),
(99796, 'ahmed khaled', 4204694, 'engamaeae@gmail.com', '010110517287', '15304560am'),
(952940, 'ahmed embaby', 5202922, 'engamaeae@gmail.com', '010110517287', '15304560am'),
(17212203, 'elshazly', 305051472, 'engamaeae@gmail.com', '010110517287', '15304560am'),
(29618948, 'سالم المصري', 966073326, 'engamaeae@gmail.com', '010110517287', '15304560am'),
(62461318, 'سالم المصري', 753969886, 'engamaeae@gmail.com', '010110517287', '15304560am'),
(71855162, 'ahmed khaled', 46575766, 'engamaeae@gmail.com', '010110517287', '15304560am'),
(86573892, 'ahmed khaled', 39875582, 'engamaeae@gmail.com', '010110517287', '15304560am'),
(188544474, 'ahmed khaled', 955302274, 'engamaeae@gmail.com', '010110517287', '15304560am'),
(211762231, 'ahmed khaled', 586089845, 'engamaeae@gmail.com', '010110517287', '15304560am'),
(236193461, 'fahd badi', 581452607, 'engamaeae@gmail.com', '010110517287', '15304560am'),
(323960964, 'xxxxxx', 823488585, 'engamaeae@gmail.com', '010110517287', '15304560am'),
(394585407, 'art', 676041301, '8', '8', '6'),
(399410439, 'ahmed khaled', 64952997, 'engamaeae@gmail.com', '010110517287', '15304560am'),
(410923168, 'kiser', 504139442, '6', '5', '65'),
(431192811, 'elshazly', 340763664, 'engamaeae@gmail.com', '010110517287', '15304560am'),
(470986877, 'ahmed khaled', 106786516, 'engamaeae@gmail.com', '010110517287', '15304560am'),
(499092235, 'ahmed khaled', 623637580, 'engamaeae@gmail.com', '010110517287', '15304560am'),
(500599740, 'abdoh', 436301577, 'dj', '02251', '5665'),
(509711381, 'ahmed khaled', 221790011, 'engamaeae@gmail.com', '010110517287', '15304560am'),
(511589450, 'abdoh', 463511883, '5', '5', '58'),
(515126820, 'elshazly', 157476154, 'engamaeae@gmail.com', '010110517287', '15304560am'),
(520776457, 'www', 656821364, 'l.', '5', 'k'),
(536450257, 'kiser', 508215761, '6', '5', '65'),
(540279061, 'fahd badi', 561497198, 'engamaeae@gmail.com', '010110517287', '15304560am'),
(588989250, 'king', 975348422, '8', '9', 'k'),
(611282905, 'qq', 30493813, 'd', '5', 'd'),
(623933997, 'ahmed khaled', 307077795, 'engamaeae@gmail.com', '010110517287', '15304560am'),
(627592713, 'elshazly', 23442805, 'engamaeae@gmail.com', '010110517287', '15304560am'),
(665877624, 'ahmed khaled', 897470346, 'engamaeae@gmail.com', '010110517287', '15304560am'),
(667024078, 'abdoh', 451412955, '5', '5', '58'),
(667080110, 'salwa', 772748498, '5', '45', 'd'),
(671635325, 'ahmed khaled', 964399585, 'engamaeae@gmail.com', '010110517287', '15304560am'),
(678298673, 'we', 227232961, 'j', '8', 'l'),
(690370503, 'elshazly', 632065941, 'engamaeae@gmail.com', '010110517287', '15304560am'),
(701202960, 'auzan', 582863521, 'engamaeae@gmail.com', '010110517287', '15304560am'),
(702278609, 'cccc', 502812584, '65', '5', 'kl'),
(787209074, 'elshazly', 443329035, 'engamaeae@gmail.com', '010110517287', '15304560am'),
(827636270, 'ahmed khaled', 313821255, 'engamaeae@gmail.com', '010110517287', '15304560am'),
(837409694, 'abdoh', 345193932, '5', '5', '58'),
(907726425, 'qq', 749385312, 'd', '5', 'd'),
(917110909, 'hana', 475762644, 'v', '5', '558'),
(933987959, 'ahmed khaled', 397916956, 'engamaeae@gmail.com', '010110517287', '15304560am'),
(941716939, 'salwa', 399311439, '5', '45', 'd'),
(943118219, 'www', 259120209, 'l.', '5', 'k'),
(954604024, 'ahmed khaled', 432638077, 'engamaeae@gmail.com', '010110517287', '15304560am'),
(980846391, 'ahmed khaled', 49592353, 'engamaeae@gmail.com', '010110517287', '15304560am'),
(995146415, 'we', 820918713, 'j', '8', 'l'),
(997985280, 'morsi', 260530632, 'd', '5', 'd');

-- --------------------------------------------------------

--
-- Table structure for table `genre`
--

CREATE TABLE `genre` (
  `genre_id` int(11) NOT NULL,
  `genre_desc` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `genre`
--

INSERT INTO `genre` (`genre_id`, `genre_desc`) VALUES
(1, 'imaginery'),
(2, 'اثارة'),
(3, 'اديان'),
(4, 'روايات'),
(5, 'قصص قصيرة');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `order_id` int(11) NOT NULL,
  `customer_id` int(11) DEFAULT NULL,
  `order_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `delivering_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`order_id`, `customer_id`, `order_date`, `delivering_date`) VALUES
(433802892, 997985280, '2019-09-27 21:04:19', NULL),
(472613686, 394585407, '2019-09-27 16:44:43', NULL),
(791997856, 995146415, '2019-10-04 01:13:27', NULL),
(907643005, 678298673, '2019-10-04 01:25:30', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `order_details`
--

CREATE TABLE `order_details` (
  `order_details_id` int(11) NOT NULL,
  `order_id` int(11) DEFAULT NULL,
  `book_id` int(11) DEFAULT NULL,
  `quantity` int(11) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `order_details`
--

INSERT INTO `order_details` (`order_details_id`, `order_id`, `book_id`, `quantity`) VALUES
(9163075, 433802892, 13, 6),
(85060431, 433802892, 9, 7),
(314089985, 907643005, 329566102, 1),
(479918764, 433802892, 11, 1),
(557988109, 791997856, 329566102, 1),
(776168200, 791997856, 62277597, 12),
(835414775, 472613686, 10, 9),
(844442037, 907643005, 62277597, 12);

-- --------------------------------------------------------

--
-- Table structure for table `publisher`
--

CREATE TABLE `publisher` (
  `publisher_id` int(11) NOT NULL,
  `name` varchar(70) NOT NULL,
  `address_id` int(11) DEFAULT NULL,
  `phone` varchar(13) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `publisher`
--

INSERT INTO `publisher` (`publisher_id`, `name`, `address_id`, `phone`) VALUES
(1, 'mohi ', 5, NULL),
(2, 'الاسعد', 4, NULL),
(3, 'الهاشم', 4, NULL),
(4, 'الهدف', 1, NULL),
(5, 'يافي ', 3, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `review`
--

CREATE TABLE `review` (
  `review_id` int(11) NOT NULL,
  `book_id` int(11) DEFAULT NULL,
  `customer_id` int(11) DEFAULT NULL,
  `review` varchar(200) DEFAULT NULL,
  `review_date` date DEFAULT curdate(),
  `degree_review` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `address`
--
ALTER TABLE `address`
  ADD PRIMARY KEY (`address_id`);

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`admin_id`);

--
-- Indexes for table `author`
--
ALTER TABLE `author`
  ADD PRIMARY KEY (`author_id`);

--
-- Indexes for table `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`book_id`),
  ADD KEY `author_id` (`author_id`),
  ADD KEY `publisher_id` (`publisher_id`),
  ADD KEY `genre_id` (`genre_id`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`customer_id`),
  ADD KEY `address_id` (`address_id`);

--
-- Indexes for table `genre`
--
ALTER TABLE `genre`
  ADD PRIMARY KEY (`genre_id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`order_id`),
  ADD KEY `customer_id` (`customer_id`);

--
-- Indexes for table `order_details`
--
ALTER TABLE `order_details`
  ADD PRIMARY KEY (`order_details_id`),
  ADD KEY `order_id` (`order_id`),
  ADD KEY `book_id` (`book_id`);

--
-- Indexes for table `publisher`
--
ALTER TABLE `publisher`
  ADD PRIMARY KEY (`publisher_id`),
  ADD UNIQUE KEY `name` (`name`),
  ADD KEY `address_id` (`address_id`);

--
-- Indexes for table `review`
--
ALTER TABLE `review`
  ADD PRIMARY KEY (`review_id`),
  ADD KEY `book_id` (`book_id`),
  ADD KEY `customer_id` (`customer_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `admin_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `book`
--
ALTER TABLE `book`
  ADD CONSTRAINT `book_ibfk_1` FOREIGN KEY (`author_id`) REFERENCES `author` (`author_id`) ON DELETE SET NULL,
  ADD CONSTRAINT `book_ibfk_2` FOREIGN KEY (`publisher_id`) REFERENCES `publisher` (`publisher_id`) ON DELETE SET NULL,
  ADD CONSTRAINT `book_ibfk_3` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`genre_id`) ON DELETE SET NULL;

--
-- Constraints for table `customer`
--
ALTER TABLE `customer`
  ADD CONSTRAINT `customer_ibfk_1` FOREIGN KEY (`address_id`) REFERENCES `address` (`address_id`) ON DELETE SET NULL;

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`);

--
-- Constraints for table `order_details`
--
ALTER TABLE `order_details`
  ADD CONSTRAINT `order_details_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `order_details_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`);

--
-- Constraints for table `publisher`
--
ALTER TABLE `publisher`
  ADD CONSTRAINT `publisher_ibfk_1` FOREIGN KEY (`address_id`) REFERENCES `address` (`address_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `review`
--
ALTER TABLE `review`
  ADD CONSTRAINT `review_ibfk_1` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `review_ibfk_2` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`) ON DELETE SET NULL;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
