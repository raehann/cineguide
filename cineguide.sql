-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 23, 2023 at 10:17 AM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.0.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cineguide`
--

-- --------------------------------------------------------

--
-- Table structure for table `film`
--

CREATE TABLE `film` (
  `id_film` varchar(255) NOT NULL,
  `judul` varchar(255) NOT NULL,
  `tahun` varchar(255) NOT NULL,
  `negara` varchar(255) NOT NULL,
  `genre` varchar(255) NOT NULL,
  `pemeran_utama` varchar(255) NOT NULL,
  `sinopsis` text NOT NULL,
  `poster` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `film`
--

INSERT INTO `film` (`id_film`, `judul`, `tahun`, `negara`, `genre`, `pemeran_utama`, `sinopsis`, `poster`) VALUES
('F-001', 'Elemental', '2023', 'USA', 'Action', 'Peter Sohn', 'In a city where fire, water, land, and air residents live together, a fiery young woman and a go-with-the-flow guy discover something elemental: how much they actually have in common.', 'src\\upload\\Elemental.jpeg'),
('F-002', 'Miss Americana', '2021', 'Jawa Timur', 'Action', 'Taylor Swift', 'Pop singer Taylor Swift reveals intimate details of her life while showcasing backstage and onstage concert footage. This is the first time you\'d ever see her storytelling throughout a real movie.', 'src\\upload\\f-002-992.jpg'),
('F-003', 'JOHN WICK', '2023', 'Amerika Serikat', 'Animation', 'Keanu', 'John Wick mengungkap jalan untuk mengalahkan The High Table. Tapi sebelum dia bisa mendapatkan kebebasannya, Wick harus menghadapi musuh baru dengan aliansi kuat di seluruh dunia dan kekuatan yang mengubah teman lama menjadi musuh.', 'src\\upload\\JOHN WICK-2023-06-14.jpg'),
('F-004', 'THE DEVIL: COMES AT NIGHT', '2023', 'Canada', 'Horror', 'Adrienne Kress, Jason Martorino, Ryan Allen', 'Seorang petinju yang sedang mencari warisannya harus berjuang untuk hidupnya ketika dia terjebak di rumah pertanian almarhum ayahnya oleh kultus kanibal setempat.', 'src\\upload\\f-004-412.jpg'),
('F-005', 'Fast X', '2023', 'USA', 'Action', 'Vin Diesel, Ludacris, Jordana Brewster', 'Selama banyak misi dan melawan rintangan yang mustahil, Dom Toretto dan keluarganya telah mengakali dan mengalahkan setiap musuh di jalan mereka ', 'src\\upload\\f-005-167.jpg'),
('F-006', 'Spider-Man: Across the Spider-Verse', '2024', 'USA', 'Action', 'Shameik Moore, Hailee Steinfeld, Brian Tyree Henry', 'Miles Morales catapults across the Multiverse, where he encounters a team of Spider-People charged with protecting its very existence.', 'src\\upload\\f-006-139.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id_user` varchar(255) NOT NULL,
  `nama` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  `foto` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id_user`, `nama`, `username`, `email`, `password`, `role`, `foto`) VALUES
('A-001', 'admin', 'admin', 'admin@mail.com', '21232f297a57a5a743894a0e4a801fc3', 'admin', ''),
('U-001', 'Raihan Ade Purnomo', 'raihan', 'raihan@gmail.com', '12345678', 'user', 'src\\upload\\U-001-740.jpg'),
('U-004', 'Raihan', 'raihan12', 'raihan@yahoo.com', '123456789', 'user', 'src\\upload\\U-004-257.jpg'),
('U-005', 'Noeni Indah S', 'noeni', 'noeni@yahoo.com', '74be16979710d4c4e7c6647856088456', 'user', 'src\\upload\\U-005-38.jpg'),
('U-006', 'Raihan', 'raihan14', 'raihanq@yahoo.com', '74be16979710d4c4e7c6647856088456', 'user', 'src\\upload\\U-006-365.png'),
('U-007', 'Azriel Ilham Ramadhan', 'azriel', 'azriel@yahoo.com', '74be16979710d4c4e7c6647856088456', 'user', 'src\\upload\\U-007-746.jpg');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `film`
--
ALTER TABLE `film`
  ADD PRIMARY KEY (`id_film`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
