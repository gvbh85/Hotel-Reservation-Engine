import { Routes, Route } from "react-router-dom";

import Navbar from "./components/Navbar";
import Footer from "./components/Footer";

import Home from "./pages/Home";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Hotels from "./pages/Hotels";
import HotelDetails from "./pages/HotelDetails";
import Booking from "./pages/Booking";
import MyBookings from "./pages/MyBookings";
import Profile from "./pages/Profile";
import NotFound from "./pages/NotFound";

function App() {
  return (
    <>
      <Navbar />

      <div className="container mt-4">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/hotels" element={<Hotels />} />
          <Route path="/hotel/:id" element={<HotelDetails />} />
          <Route path="/booking" element={<Booking />} />
          <Route path="/my-bookings" element={<MyBookings />} />
          <Route path="/profile" element={<Profile />} />
          <Route path="*" element={<NotFound />} />
        </Routes>
      </div>

      <Footer />
    </>
  );
}

export default App;