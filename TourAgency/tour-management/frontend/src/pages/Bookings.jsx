import React, { useState, useEffect } from "react";
import axios from "axios";
import "../styles/bookings.css"; // Создайте стиль для таблицы

const Bookings = () => {
    const [bookings, setBookings] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchBookings = async () => {
            try {
                const response = await axios.get("http://localhost:8085/api/bookings");
                setBookings(response.data);
            } catch (error) {
                console.error("Error fetching bookings:", error);
            } finally {
                setLoading(false);
            }
        };

        fetchBookings();
    }, []);

    return (
        <div className="bookings-container">
            {loading ? (
                <p>Загрузка покупок...</p>
            ) : (
                <table className="bookings-table">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Title</th>
                        <th>Full name</th>
                        <th>Date</th>
                        <th>Number of people</th>
                        <th>Total cost</th>
                        <th>Phone</th>
                    </tr>
                    </thead>
                    <tbody>
                    {bookings.map((booking) => (
                        <tr key={booking.id}>
                            <td>{booking.id}</td>
                            <td>{booking.tour.title}</td>
                            <td>{booking.fullName}</td>
                            <td>{booking.bookAt}</td>
                            <td>{booking.numPeople}</td>
                            <td>${booking.totalPrice}</td>
                            <td>{booking.phone}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}
        </div>
    );
};

export default Bookings;
