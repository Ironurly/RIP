import React from "react"
import {Routes, Route, Navigate} from 'react-router-dom'

import Home from './../pages/Home'
import Tours from './../pages/Tours'
import TourDetails from './../pages/TourDetails'
import Login from './../pages/Login'
import Register from './../pages/Register'
import SearchResultList from './../pages/SearchResultList'
import ThankYou from "../pages/ThankYou"
import AddTour from './../pages/AddTour'
import ThankYouSub from "../pages/ThankYouSub";
import About from "../pages/About";
import Bookings from "../pages/Bookings";

const Routers = ({isAuthenticated, setIsAuthenticated}) => {// Принимаем prop isAuthenticated
    return (
        <Routes>
            <Route path="/" element={<Navigate to="/home"/>}/>
            <Route path="/home" element={<Home/>}/>
            <Route path="/tours" element={<Tours/>}/>
            <Route path="/tours/:id" element={<TourDetails/>}/>
            <Route path="/login" element={isAuthenticated ? <Navigate to="/home" replace={true}/> :
                <Login setIsAuthenticated={setIsAuthenticated}/>}/> {/* Передаем setIsAuthenticated */}
            <Route path="/register" element={isAuthenticated ? <Navigate to="/home" replace={true}/> : <Register/>}/>
            <Route path="/thank-you" element={<ThankYou/>}/>
            <Route path="/thank-you-sub" element={<ThankYouSub/>}/>
            <Route path="/tours/search" element={<SearchResultList/>}/>
            <Route path="/add-tour" element={<AddTour/>}/>
            <Route path="/about" element={<About/>}/>
            <Route path="/show-books" element={<Bookings/>}/>
            {/* Защищенные маршруты */}
            {/* <Route path="/protected-route" element={isAuthenticated ? <ProtectedRoute /> : <Navigate to="/login" />} /> */}
        </Routes>
    );
};

export default Routers;