-- phpMyAdmin SQL Dump
-- version 4.9.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jul 05, 2020 at 05:06 PM
-- Server version: 10.3.16-MariaDB
-- PHP Version: 7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id12751880_trainit_database`
--

-- --------------------------------------------------------

--
-- Table structure for table `rtb_session_sessionData`
--

CREATE TABLE `rtb_session_sessionData` (
  `id` int(11) NOT NULL,
  `sessionID` int(11) NOT NULL,
  `sessionDataID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tb_Address`
--

CREATE TABLE `tb_Address` (
  `id` int(11) NOT NULL,
  `parentID` int(11) NOT NULL,
  `addressName` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_Address`
--

INSERT INTO `tb_Address` (`id`, `parentID`, `addressName`) VALUES
(1, 0, 'Egypt'),
(2, 1, 'Cairo'),
(3, 2, 'Nasr City'),
(4, 5, 'Maadi'),
(5, 1, 'Alexandria');

-- --------------------------------------------------------

--
-- Table structure for table `tb_ClassificationResult`
--

CREATE TABLE `tb_ClassificationResult` (
  `id` int(11) NOT NULL,
  `date` date NOT NULL,
  `errorType` varchar(100) DEFAULT NULL,
  `isMistake` tinyint(4) NOT NULL,
  `strokeType` varchar(100) NOT NULL,
  `sessionDataID` int(11) NOT NULL,
  `playerID` int(11) NOT NULL,
  `readed` tinyint(4) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_ClassificationResult`
--

INSERT INTO `tb_ClassificationResult` (`id`, `date`, `errorType`, `isMistake`, `strokeType`, `sessionDataID`, `playerID`, `readed`) VALUES
(1, '2020-05-19', NULL, 0, 'Forehand Drive', 1, 2, 1),
(2, '2020-05-19', 'Waist Error', 1, 'Backhand Push', 1, 2, 1),
(3, '2020-05-19', NULL, 0, 'aaaa', 1, 2, 1),
(4, '2020-05-20', NULL, 0, 'Forehand', 1, 2, 0);

-- --------------------------------------------------------

--
-- Table structure for table `tb_Club`
--

CREATE TABLE `tb_Club` (
  `id` int(11) NOT NULL,
  `addressID` int(11) NOT NULL,
  `clubName` varchar(255) NOT NULL,
  `clubRate` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_Club`
--

INSERT INTO `tb_Club` (`id`, `addressID`, `clubName`, `clubRate`) VALUES
(1, 1, 'Al Ahli Club', 100),
(2, 1, 'Zamalek', 0),
(3, 1, 'pyramid', 50);

-- --------------------------------------------------------

--
-- Table structure for table `tb_Notification`
--

CREATE TABLE `tb_Notification` (
  `id` int(11) NOT NULL,
  `fromUserID` int(11) NOT NULL,
  `toUserID` int(11) NOT NULL,
  `message` varchar(250) NOT NULL,
  `subject` varchar(250) NOT NULL,
  `isRead` tinyint(4) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_Notification`
--

INSERT INTO `tb_Notification` (`id`, `fromUserID`, `toUserID`, `message`, `subject`, `isRead`) VALUES
(1, 1, 3, 'You Got Training on 6:00 pm next Wednesday', 'Training Time', 0),
(2, 1, 3, 'Due to the rain and weather condition please be informed and the training of today\'s is canceled, take your day OFF', 'Training Canceled', 1);

-- --------------------------------------------------------

--
-- Table structure for table `tb_Players`
--

CREATE TABLE `tb_Players` (
  `id` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `coachedBy` int(11) DEFAULT NULL,
  `playerNumber` int(11) NOT NULL DEFAULT 0,
  `level` int(11) NOT NULL DEFAULT 0,
  `handUsed` varchar(8) NOT NULL,
  `isdeleted` tinyint(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_Players`
--

INSERT INTO `tb_Players` (`id`, `userID`, `coachedBy`, `playerNumber`, `level`, `handUsed`, `isdeleted`) VALUES
(1, 2, 1, 112, 0, 'left', 0),
(2, 3, 1, 113, 0, 'right', 0),
(3, 4, 1, 112, 1, 'left', 0),
(4, 6, 1, 117, 5, 'right', 0),
(6, 5, 1, 115, 4, 'left', 0),
(7, 7, 1, 1, 1, 'right', 0);

-- --------------------------------------------------------

--
-- Table structure for table `tb_Report`
--

CREATE TABLE `tb_Report` (
  `id` int(11) NOT NULL,
  `playerID` int(11) NOT NULL,
  `date` date NOT NULL,
  `numberOfCorrectStrokes` int(11) NOT NULL,
  `numberOfWrongStrokes` int(11) NOT NULL,
  `errorRatio` double NOT NULL,
  `performanceRate` int(11) NOT NULL,
  `TotalNumberOfStrokes` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_Report`
--

INSERT INTO `tb_Report` (`id`, `playerID`, `date`, `numberOfCorrectStrokes`, `numberOfWrongStrokes`, `errorRatio`, `performanceRate`, `TotalNumberOfStrokes`) VALUES
(1, 2, '2020-03-01', 40, 60, 0.6, 88, 100),
(2, 2, '2020-05-13', 34, 88, 0.55, 33, 112),
(3, 2, '2020-05-12', 99, 51, 0.2, 55, 150),
(4, 2, '2020-05-11', 2, 1, 0.1, 99, 3),
(5, 2, '2020-05-10', 11, 11, 0.5, 60, 22),
(6, 2, '2020-02-25', 50, 50, 0.6, 50, 100),
(7, 2, '2020-02-24', 20, 10, 0.45, 65, 30),
(8, 2, '2020-05-19', 13, 4, 0.3333333333333333, 64, 16),
(10, 2, '2020-05-20', 1, 1, 0.9, 99, 1);

-- --------------------------------------------------------

--
-- Table structure for table `tb_Session`
--

CREATE TABLE `tb_Session` (
  `id` int(11) NOT NULL,
  `playerID` int(11) NOT NULL,
  `date` date NOT NULL DEFAULT current_timestamp(),
  `startSessionTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `endSessionTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `numberOfCorrectStrokes` int(11) NOT NULL,
  `numberOfWrongStrokes` int(11) NOT NULL,
  `totalNumberOfStrokes` int(11) NOT NULL,
  `errorRatio` double NOT NULL,
  `percentageRate` decimal(10,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_Session`
--

INSERT INTO `tb_Session` (`id`, `playerID`, `date`, `startSessionTime`, `endSessionTime`, `numberOfCorrectStrokes`, `numberOfWrongStrokes`, `totalNumberOfStrokes`, `errorRatio`, `percentageRate`) VALUES
(1, 2, '2020-05-19', '2020-05-19 19:55:03', '2020-05-19 20:05:03', 5, 2, 7, 0.6, 95),
(2, 2, '2020-05-19', '2020-05-19 20:09:13', '2020-05-19 20:21:21', 7, 1, 8, 0.3, 97),
(3, 2, '2020-05-19', '2020-05-19 20:22:12', '2020-05-19 20:31:07', 1, 1, 1, 0.1, 0),
(4, 2, '2020-05-20', '2020-05-20 13:40:13', '2020-05-20 13:59:25', 1, 1, 1, 0.9, 99);

--
-- Triggers `tb_Session`
--
DELIMITER $$
CREATE TRIGGER `ayhaga` AFTER INSERT ON `tb_Session` FOR EACH ROW IF New.date NOT IN(SELECT date FROM tb_Report where NEW.date = tb_Report.date) THEN INSERT INTO `tb_Report`(`playerID`, `date`, `numberOfCorrectStrokes`, `numberOfWrongStrokes`, `errorRatio`, `performanceRate`, `TotalNumberOfStrokes`) VALUES (
        NEW.playerID, NEW.date, NEW.numberOfCorrectStrokes, NEW.numberOfWrongStrokes, NEW.errorRatio, NEW.percentageRate, NEW.TotalNumberOfStrokes);

ELSE

UPDATE tb_Report SET tb_Report.numberOfCorrectStrokes = (SELECT SUM(tb_Session.numberOfCorrectStrokes) FROM tb_Session WHERE tb_Session.playerID = NEW.playerID AND tb_Session.date = NEW.date) , tb_Report.numberOfWrongStrokes = (SELECT SUM(tb_Session.numberOfWrongStrokes) FROM tb_Session WHERE tb_Session.playerID = NEW.playerID AND tb_Session.date = NEW.date) ,
tb_Report.TotalNumberOfStrokes = (SELECT SUM(tb_Session.TotalNumberOfStrokes) FROM tb_Session WHERE tb_Session.playerID = NEW.playerID AND tb_Session.date = NEW.date) ,
tb_Report.errorRatio = (SELECT SUM(tb_Session.errorRatio)/ COUNT(tb_Session.errorRatio) FROM tb_Session WHERE tb_Session.playerID = NEW.playerID AND tb_Session.date = NEW.date) ,
tb_Report.performanceRate = (SELECT SUM(tb_Session.percentageRate)/ COUNT(tb_Session.percentageRate) FROM tb_Session WHERE tb_Session.playerID = NEW.playerID AND tb_Session.date = NEW.date) 
WHERE tb_Report.playerID = NEW.playerID AND tb_Report.date = NEW.date;


END IF
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `tb_SessionData`
--

CREATE TABLE `tb_SessionData` (
  `id` int(11) NOT NULL,
  `StrokeDataJson` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_SessionData`
--

INSERT INTO `tb_SessionData` (`id`, `StrokeDataJson`) VALUES
(1, '-0.0564998  -0.324918  1.431643  0.03222878  -0.4547916  1.628172  -0.05044648  -0.5088449  1.798992  -0.3658001  -0.6448335  1.886307  -0.148552  -0.6532691  1.868426  63709344164543  -0.05875401  -0.3252268  1.428483  0.02198568  -0.4568682  1.627013  -0.05443352  -0.5145963  1.799457  -0.365083  -0.6435171  1.87591  -0.1600864  -0.6515733  1.874247  63709344164581  -0.0938441  -0.3283811  1.40611  -0.007568281  -0.4565398  1.604282  -0.04647932  -0.5285509  1.784201  -0.3158861  -0.6946884  1.901108  -0.1700613  -0.6671184  1.805816  63709344164612  -0.2182252  -0.4235739  1.474889  -0.02641065  -0.5014861  1.704466  -0.1085239  -0.4964861  1.884194  -0.3244677  -0.715161  1.912354  -0.1924883  -0.6849482  1.78152  63709344164717  -0.2237692  -0.4355797  1.470901  -0.03029148  -0.5040572  1.701976  -0.1157534  -0.4966595  1.879861  -0.3369136  -0.6996448  1.883741  -0.217714  -0.6667863  1.83748  63709344164747  -0.2225545  -0.4597642  1.468407  -0.05358285  -0.506101  1.714186  -0.1521663  -0.4892016  1.883806  -0.3446123  -0.7005525  1.880725  -0.2208032  -0.669692  1.840557  63709344164779  -0.2200531  -0.4735499  1.46806  -0.06512455  -0.5133399  1.709643  -0.1652262  -0.4909727  1.877515  -0.3677191  -0.7014679  1.872241  -0.2372663  -0.6892834  1.841841  63709344164814  -0.217798  -0.4875334  1.469821  -0.05669865  -0.5670652  1.788554  -0.1706151  -0.4939684  1.87259  -0.3787941  -0.7024416  1.869702  -0.2440205  -0.6951705  1.843465  63709344164847  -0.04187313  -0.6181394  1.522774  -0.05742412  -0.6048513  1.774816  -0.190997  -0.5209982  1.842188  -0.3824671  -0.6612251  1.831986  -0.2656024  -0.6483757  1.787768  63709344164954  '),
(2, '-0.3031218  -0.5905836  1.515674  -0.2432105  -0.4431215  1.344186  -0.2262543  -0.3411098  1.471673  -0.3881154  -0.4048686  1.371198  -0.3436603  -0.4570616  1.377664  63709343572943  -0.2794612  -0.5362613  1.421545  -0.1969043  -0.3899761  1.24946  -0.2264058  -0.3524957  1.465615  -0.3917021  -0.4049492  1.364799  -0.3453916  -0.4587868  1.368721  63709343572976  -0.2999831  -0.5414747  1.408165  -0.209863  -0.3928958  1.243891  -0.2301275  -0.358307  1.461743  -0.4003449  -0.4087283  1.34632  -0.3532525  -0.462796  1.338677  63709343573014  -0.2989967  -0.5437081  1.403817  -0.21029  -0.3934835  1.239765  -0.2351969  -0.361592  1.457561  -0.3989108  -0.4126238  1.306758  -0.3540845  -0.463958  1.33831  63709343573045  -0.1715398  -0.3652686  1.071954  -0.2212119  -0.3819503  1.233803  -0.2462245  -0.3612528  1.453004  -0.3979771  -0.4115195  1.305193  -0.3538429  -0.4639935  1.336427  63709343573079  -0.1898587  -0.3455229  1.059228  -0.2323428  -0.3488331  1.228346  -0.2506604  -0.3567874  1.449085  -0.400429  -0.4106318  1.308851  -0.3501308  -0.4611689  1.325215  63709343573146  -0.2394032  -0.2665834  1.029799  -0.2361237  -0.3397875  1.226606  -0.2568661  -0.3416482  1.447194  -0.4062195  -0.3914959  1.352862  -0.3414606  -0.4417108  1.309472  63709343573182  -0.4103297  -0.2956285  1.054846  -0.2533682  -0.3093457  1.286906  -0.2486121  -0.3421406  1.505941  -0.4026153  -0.3788733  1.370411  -0.3358961  -0.4293107  1.315913  63709343573214  -0.4145226  -0.2956232  1.060245  -0.2523996  -0.2979475  1.289106  -0.2423333  -0.3298351  1.508149  -0.4038905  -0.372411  1.374138  -0.3314251  -0.4152995  1.324065  63709343573252  '),
(3, '-0.3440453  -0.4063039  1.157351  -0.1519663  -0.4047048  1.284148  -0.1715211  -0.3815793  1.503234  -0.3336131  -0.4363756  1.364589  -0.287568  -0.4879686  1.414506  63709343566678  -0.3284815  -0.3285099  1.122625  -0.1469233  -0.4148393  1.27179  -0.1659119  -0.3976995  1.491462  -0.3293778  -0.416207  1.350089  -0.285094  -0.4759812  1.377514  63709343566711  -0.4550487  -0.2585918  1.023782  -0.1527067  -0.4149328  1.265242  -0.1687045  -0.4006333  1.48536  -0.3380836  -0.4144309  1.343445  -0.2850641  -0.4792268  1.327431  63709343566745  -0.4619732  -0.25709  1.021304  -0.1572882  -0.4146668  1.25744  -0.1749774  -0.4015425  1.47743  -0.3391832  -0.4132241  1.343515  -0.2848216  -0.4761584  1.321684  63709343566778  -0.4870743  -0.24428  1.042283  -0.179992  -0.36932  1.246612  -0.1922895  -0.3827791  1.466753  -0.345162  -0.4138852  1.348711  -0.2847227  -0.4693728  1.321462  63709343566881  -0.5311836  -0.2483196  1.06729  -0.1994232  -0.3425763  1.246589  -0.2087464  -0.3856565  1.462673  -0.3497199  -0.3996701  1.361323  -0.2818229  -0.4548745  1.347499  63709343566914  -0.5408112  -0.2503092  1.08105  -0.30101  -0.3094501  1.273007  -0.2287755  -0.3280703  1.479648  -0.3462014  -0.3952621  1.364796  -0.287795  -0.4358969  1.351108  63709343566946  '),
(4, '0.0109977  -0.6520631  1.577099  0.0140393  -0.5584263  1.387145  -0.1162563  -0.4829369  1.388667  -0.3423296  -0.7353451  1.791083  -0.191105  -0.6659854  1.7396  63709344162580  0.005723778  -0.6526417  1.572765  0.008755608  -0.5590291  1.38289  -0.12154  -0.4835398  1.384422  -0.34089  -0.7359916  1.792505  -0.1913919  -0.6665248  1.738909  63709344162612  -6.887689E-05  -0.6577784  1.577051  0.00290587  -0.5641292  1.387251  -0.1273897  -0.4886398  1.388789  -0.3435624  -0.7393941  1.799319  -0.1915539  -0.6668725  1.739125  63709344162646  -0.005552312  -0.6588964  1.588293  -0.002554811  -0.5653445  1.398614  -0.1328504  -0.4898551  1.400115  -0.3608025  -0.7451185  1.813859  -0.2121014  -0.6789078  1.753525  63709344162677  -0.01117685  -0.6597846  1.602035  -0.008144613  -0.5663551  1.412514  -0.1384402  -0.4908658  1.414012  -0.3566949  -0.7252612  1.829582  -0.2140698  -0.6587694  1.764004  63709344162712  -0.04862161  -0.5976535  1.601921  -0.04559918  -0.5042729  1.412519  -0.1758948  -0.4287835  1.414008  -0.3650401  -0.7295047  1.823988  -0.2158003  -0.6657759  1.788494  63709344162759  ');

-- --------------------------------------------------------

--
-- Table structure for table `tb_Users`
--

CREATE TABLE `tb_Users` (
  `id` int(11) NOT NULL,
  `firstName` varchar(50) NOT NULL,
  `lastName` varchar(50) NOT NULL,
  `email` varchar(250) NOT NULL,
  `password` varchar(250) NOT NULL,
  `phoneNumber` varchar(15) NOT NULL,
  `birthdate` date NOT NULL,
  `gender` varchar(10) NOT NULL,
  `usertypeID` int(11) NOT NULL,
  `addressID` int(11) NOT NULL,
  `user_picture` blob DEFAULT NULL,
  `clubID` int(11) NOT NULL,
  `isdeleted` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_Users`
--

INSERT INTO `tb_Users` (`id`, `firstName`, `lastName`, `email`, `password`, `phoneNumber`, `birthdate`, `gender`, `usertypeID`, `addressID`, `user_picture`, `clubID`, `isdeleted`) VALUES
(1, 'Habiba', 'Hegazy', 'habiba@gmail.com', '123', '01094586906', '2020-01-07', 'Female', 2, 3, '', 1, 0),
(2, 'aa', 'Yassin', 'AhmedYassin@gmail.com', '123', '123', '2020-02-04', 'Male', 1, 3, '', 1, 0),
(3, 'Ibrahim', 'Fawzy', 'ibrahimFawzy@gmail.com', '123', '123', '2020-02-03', 'Male', 1, 3, '', 1, 0),
(4, 'Kareem', 'Mohsen', 'kareem_mohsen@gmail.com', '123', '123', '2020-01-07', 'Male', 3, 4, NULL, 1, 0),
(5, 'Seif', 'Elmosalamy', 'seif@gmail.com', '123', '01201815059', '2020-01-07', 'Male', 2, 3, NULL, 1, 0),
(6, 'Moustafa', 'Hussen', 'moustafa@gmail.com', '123', '123', '2020-01-07', 'Male', 4, 3, NULL, 1, 0),
(7, 'Mohammed', 'abdelsalm', 'abdelsalam@gmail.com', '23456', '12545565', '2020-04-01', 'Male', 1, 3, NULL, 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `tb_UserType`
--

CREATE TABLE `tb_UserType` (
  `id` int(11) NOT NULL,
  `usertypeName` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_UserType`
--

INSERT INTO `tb_UserType` (`id`, `usertypeName`) VALUES
(1, 'Player'),
(2, 'Coach'),
(3, 'Club Manager'),
(4, 'Admin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `rtb_session_sessionData`
--
ALTER TABLE `rtb_session_sessionData`
  ADD PRIMARY KEY (`id`),
  ADD KEY `sessionID` (`sessionID`),
  ADD KEY `sessionDataID` (`sessionDataID`);

--
-- Indexes for table `tb_Address`
--
ALTER TABLE `tb_Address`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tb_ClassificationResult`
--
ALTER TABLE `tb_ClassificationResult`
  ADD PRIMARY KEY (`id`),
  ADD KEY `sessionDataID` (`sessionDataID`),
  ADD KEY `playerID` (`playerID`);

--
-- Indexes for table `tb_Club`
--
ALTER TABLE `tb_Club`
  ADD PRIMARY KEY (`id`),
  ADD KEY `addressID` (`addressID`);

--
-- Indexes for table `tb_Notification`
--
ALTER TABLE `tb_Notification`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fromUserID` (`fromUserID`),
  ADD KEY `toUserID` (`toUserID`);

--
-- Indexes for table `tb_Players`
--
ALTER TABLE `tb_Players`
  ADD PRIMARY KEY (`id`),
  ADD KEY `userID` (`userID`),
  ADD KEY `coachedBy` (`coachedBy`);

--
-- Indexes for table `tb_Report`
--
ALTER TABLE `tb_Report`
  ADD PRIMARY KEY (`id`),
  ADD KEY `playerID` (`playerID`);

--
-- Indexes for table `tb_Session`
--
ALTER TABLE `tb_Session`
  ADD PRIMARY KEY (`id`),
  ADD KEY `playerID` (`playerID`);

--
-- Indexes for table `tb_SessionData`
--
ALTER TABLE `tb_SessionData`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tb_Users`
--
ALTER TABLE `tb_Users`
  ADD PRIMARY KEY (`id`),
  ADD KEY `addressID` (`addressID`),
  ADD KEY `usertypeID` (`usertypeID`),
  ADD KEY `clubID` (`clubID`);

--
-- Indexes for table `tb_UserType`
--
ALTER TABLE `tb_UserType`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `rtb_session_sessionData`
--
ALTER TABLE `rtb_session_sessionData`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tb_Address`
--
ALTER TABLE `tb_Address`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=112;

--
-- AUTO_INCREMENT for table `tb_ClassificationResult`
--
ALTER TABLE `tb_ClassificationResult`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `tb_Club`
--
ALTER TABLE `tb_Club`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `tb_Notification`
--
ALTER TABLE `tb_Notification`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `tb_Players`
--
ALTER TABLE `tb_Players`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `tb_Report`
--
ALTER TABLE `tb_Report`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `tb_Session`
--
ALTER TABLE `tb_Session`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `tb_SessionData`
--
ALTER TABLE `tb_SessionData`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `tb_Users`
--
ALTER TABLE `tb_Users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `tb_UserType`
--
ALTER TABLE `tb_UserType`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `rtb_session_sessionData`
--
ALTER TABLE `rtb_session_sessionData`
  ADD CONSTRAINT `rtb_session_sessionData_ibfk_1` FOREIGN KEY (`sessionID`) REFERENCES `tb_Session` (`id`),
  ADD CONSTRAINT `rtb_session_sessionData_ibfk_2` FOREIGN KEY (`sessionDataID`) REFERENCES `tb_SessionData` (`id`);

--
-- Constraints for table `tb_ClassificationResult`
--
ALTER TABLE `tb_ClassificationResult`
  ADD CONSTRAINT `tb_ClassificationResult_ibfk_1` FOREIGN KEY (`sessionDataID`) REFERENCES `tb_SessionData` (`id`),
  ADD CONSTRAINT `tb_ClassificationResult_ibfk_2` FOREIGN KEY (`playerID`) REFERENCES `tb_Players` (`id`);

--
-- Constraints for table `tb_Club`
--
ALTER TABLE `tb_Club`
  ADD CONSTRAINT `tb_Club_ibfk_1` FOREIGN KEY (`addressID`) REFERENCES `tb_Address` (`id`);

--
-- Constraints for table `tb_Notification`
--
ALTER TABLE `tb_Notification`
  ADD CONSTRAINT `tb_Notification_ibfk_1` FOREIGN KEY (`fromUserID`) REFERENCES `tb_Users` (`id`),
  ADD CONSTRAINT `tb_Notification_ibfk_2` FOREIGN KEY (`toUserID`) REFERENCES `tb_Users` (`id`);

--
-- Constraints for table `tb_Players`
--
ALTER TABLE `tb_Players`
  ADD CONSTRAINT `tb_Players_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `tb_Users` (`id`),
  ADD CONSTRAINT `tb_Players_ibfk_2` FOREIGN KEY (`coachedBy`) REFERENCES `tb_Users` (`id`);

--
-- Constraints for table `tb_Report`
--
ALTER TABLE `tb_Report`
  ADD CONSTRAINT `tb_Report_ibfk_1` FOREIGN KEY (`playerID`) REFERENCES `tb_Players` (`id`);

--
-- Constraints for table `tb_Session`
--
ALTER TABLE `tb_Session`
  ADD CONSTRAINT `tb_Session_ibfk_3` FOREIGN KEY (`playerID`) REFERENCES `tb_Players` (`id`);

--
-- Constraints for table `tb_Users`
--
ALTER TABLE `tb_Users`
  ADD CONSTRAINT `tb_Users_ibfk_2` FOREIGN KEY (`usertypeID`) REFERENCES `tb_UserType` (`id`),
  ADD CONSTRAINT `tb_Users_ibfk_3` FOREIGN KEY (`addressID`) REFERENCES `tb_Address` (`id`),
  ADD CONSTRAINT `tb_Users_ibfk_4` FOREIGN KEY (`clubID`) REFERENCES `tb_Club` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
