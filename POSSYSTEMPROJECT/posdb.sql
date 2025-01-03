-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 18, 2023 at 03:54 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `posdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `billtbl`
--

CREATE TABLE `billtbl` (
  `BNum` int(11) NOT NULL,
  `BDate` varchar(100) NOT NULL,
  `CustId` int(11) NOT NULL,
  `CustName` varchar(30) NOT NULL,
  `PayMethod` varchar(30) NOT NULL,
  `Amt` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `customertbl`
--

CREATE TABLE `customertbl` (
  `CustId` int(11) NOT NULL,
  `CustName` varchar(50) NOT NULL,
  `CustAdd` varchar(100) NOT NULL,
  `CustPhone` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customertbl`
--

INSERT INTO `customertbl` (`CustId`, `CustName`, `CustAdd`, `CustPhone`) VALUES
(1, 'sami', 'sabir sre', '03448898'),
(2, 'saad', 'kabari market', '0364'),
(3, 'Fahad', 'model colony', '03479'),
(4, 'usman', 'nazimabad no 2', '034433992');

-- --------------------------------------------------------

--
-- Table structure for table `producttbl`
--

CREATE TABLE `producttbl` (
  `PId` int(11) NOT NULL,
  `PName` varchar(50) NOT NULL,
  `PCat` varchar(30) NOT NULL,
  `PPrice` int(11) NOT NULL,
  `PQty` int(11) NOT NULL,
  `SupplierId` int(11) NOT NULL,
  `SupName` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `producttbl`
--

INSERT INTO `producttbl` (`PId`, `PName`, `PCat`, `PPrice`, `PQty`, `SupplierId`, `SupName`) VALUES
(1, 'Burger', 'Food', 120, 48, 2, 'sami'),
(2, 'Oven', 'Electronics', 5000, 24, 3, 'zohaib'),
(3, 'lipstick', 'Fashion', 500, 40, 1, 'owais'),
(4, 'chips', 'Food', 50, 250, 3, 'zohaib');

-- --------------------------------------------------------

--
-- Table structure for table `suppliertb`
--

CREATE TABLE `suppliertb` (
  `SupId` int(11) NOT NULL,
  `SupName` varchar(50) NOT NULL,
  `SupAddress` varchar(100) NOT NULL,
  `SupPhone` varchar(11) NOT NULL,
  `SupRemarks` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `suppliertb`
--

INSERT INTO `suppliertb` (`SupId`, `SupName`, `SupAddress`, `SupPhone`, `SupRemarks`) VALUES
(1, 'owais', 'nazimabad No2', '0433', 'good'),
(2, 'sami', 'sabir', '9228', 'amazing'),
(3, 'zohaib', 'sabir sre', '123', 'goood'),
(4, 'Omair', 'Karsaz', '0344555', 'good');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customertbl`
--
ALTER TABLE `customertbl`
  ADD PRIMARY KEY (`CustId`);

--
-- Indexes for table `producttbl`
--
ALTER TABLE `producttbl`
  ADD PRIMARY KEY (`PId`);

--
-- Indexes for table `suppliertb`
--
ALTER TABLE `suppliertb`
  ADD PRIMARY KEY (`SupId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
