import React, {useState} from "react";
import "./booking.css";
import {Form, FormGroup, ListGroup, ListGroupItem, Button} from "reactstrap";
import {useNavigate} from "react-router-dom";
import axios from "axios";

const Booking = ({tour, avgRating}) => {
    const {price, maxPeople} = tour;
    const navigate = useNavigate()

    const [bookingData, setBookingData] = useState({
        tour: {id: tour.id},
        fullName: '',
        phone: '',
        bookAt: '',
        numPeople: 1,
        totalPrice: price,
    });

    const handleChange = (e) => {
        setBookingData((prev) => ({...prev, [e.target.id]: e.target.value}));
    };

    const handleChange2 = (e) => {
        let numValue = parseInt(e.target.value, 10) || 1; // default to 1 if not an integer
        numValue = Math.max(1, numValue);
        numValue = Math.min(maxPeople, numValue);
        setBookingData(prev => ({...prev, numPeople: numValue, totalPrice: tour.price * numValue}));
        e.target.value = numValue;// !!!  Обновляем значение в поле ввода
    };


    const serviceFee = 10
    const totalAmount = bookingData.totalPrice + serviceFee;

    //send data to server

    const handleClick = async (e) => {
        e.preventDefault();
        console.log(bookingData)
        try {

            const response = await axios.post('http://localhost:8085/api/bookings', bookingData, {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('token')}`
                }

            });
            // Сбрасываем данные формы после успешного бронирования
            setBookingData({
                tour: {id: tour.id},
                fullName: "",
                phone: "",
                bookAt: "",
                numPeople: 1,
                totalPrice: price
            });

            navigate("/thank-you");

        } catch (error) {
            console.error("Error adding booking:", error);
            if (error.response) {
                console.error("Server responded with:", error.response.data);
            }
        }
    };


    return (
        <div className="booking">
            <div className="booking__top d-flex align-items-center justify-content-between">
                <h3>
                    ${price}
                </h3>
                <span className="tour__rating d-flex align-items-center">
          <i className="ri-star-fill"></i> {avgRating === 0 ? null : avgRating}{" "}
        </span>
            </div>

            {/*================= booking form start ===================== */}
            <div className="booking__form">
                <h5>Information</h5>

                <Form className="booking_info-form" onSubmit={handleClick}>
                    <FormGroup>
                        <input
                            type="text"
                            placeholder="Full Name"
                            id="fullName"
                            required
                            onChange={handleChange}
                        />
                    </FormGroup>

                    <FormGroup>
                        <input
                            type="text"
                            placeholder="Phone"
                            id="phone"
                            required
                            onChange={handleChange}
                        />
                    </FormGroup>

                    <FormGroup className="d-flex align-items-center gap-3">
                        <input
                            type="date"
                            placeholder=""
                            id="bookAt"
                            required
                            onChange={handleChange}
                        />

                        <input
                            type="number"
                            placeholder="Guests"
                            id="numPeople"
                            required
                            onChange={handleChange2}
                        />
                    </FormGroup>
                    <ListGroup>
                        <ListGroupItem className="border-0 px-0">
                            <h5 className="d-flex align-items-center gap-1">
                                ${price} <i className="ri-close-line"></i> 1 person
                            </h5>
                            <span>${price}</span>
                        </ListGroupItem>
                        <ListGroupItem className="border-0 px-0">
                            <h5>Service charge</h5>
                            <span>${serviceFee}</span>
                        </ListGroupItem>
                        <ListGroupItem className="border-0 px-0 total">
                            <h5>Total</h5>
                            <span>${totalAmount}</span>
                        </ListGroupItem>
                    </ListGroup>

                    <Button className="btn primary__btn w-100 mt-4" type="submit">
                        Book Now
                    </Button>
                </Form>

            </div>
            {/*================= booking form end ===================== */}
            {/*================= booking bottom start ===================== */}
            <div className="booking__bottom">

            </div>
            {/*================= booking bottom end ===================== */}
        </div>
    );
};

export default Booking;
