import React, {useRef, useState, useEffect} from "react";
import "../styles/tour-details.css";
import {Container, Row, Col, Form, ListGroup, Button} from "reactstrap";
import {useParams, useNavigate} from "react-router-dom";
import calculateAvgRating from "../utils/avgRating";
import avatar from "../assets/images/avatar.jpg";
import Booking from "../components/Booking/Booking";
import Newsletter from "../shared/Newsletter";
import axios from "axios";

const TourDetails = () => {
    const {id} = useParams();
    const navigate = useNavigate();
    const reviewMsgRef = useRef("");
    const [tourRating, setTourRating] = useState(null); // Хранит текущий рейтинг
    const [hoverRating, setHoverRating] = useState(null); // Новый state для подсветки
    const [reviews, setReviews] = useState([]);
    const [tour, setTour] = useState(null);
    const [loading, setLoading] = useState(true);
    const [isAdmin, setIsAdmin] = useState(false);


    useEffect(() => {
        const fetchTour = async () => {
            try {
                const response = await axios.get(`http://localhost:8085/api/tours/${id}`);
                setTour(response.data);
            } catch (error) {
                console.error("Error fetching tour details:", error);
                navigate("/page-not-found");
            } finally {
                setLoading(false);
            }
        };

        fetchTour();
    }, [id, navigate]);

    useEffect(() => {
        const fetchReviews = async () => {
            try {
                const response = await axios.get(`http://localhost:8085/api/tours/${id}/reviews`);
                setReviews(response.data);
            } catch (error) {
                console.error("Error fetching reviews:", error);
            }

            const storedIsAdmin = localStorage.getItem("isAdmin");

            setIsAdmin(storedIsAdmin === 'true');
        };

        if (id) {
            fetchReviews();
        }
    }, [id]);

    // submit request to the server
    const submitHandler = async (e) => {
        e.preventDefault();

        const reviewText = reviewMsgRef.current.value;
        const newReview = {
            tour: {id: id},
            comment: reviewText,
            rating: tourRating,
        };

        try {
            const response = await axios.post("http://localhost:8085/api/reviews", newReview, {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem("token")}`,
                },
            });

            console.log("Review created successfully:", response.data);
            setReviews([...reviews, response.data]);

            // Clear form
            reviewMsgRef.current.value = "";
            setTourRating(null);
        } catch (error) {
            console.error("Error adding review:", error);
            if (error.response) {
                console.error("Server responded with:", error.response.data);
            }
        }
    };


    const handleDeleteTour = async () => { // !!! Функция для удаления тура
        console.log("Deleting tour with ID:", id);

        try {

            await axios.delete(`http://localhost:8085/api/tours/${id}`, {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('token')}`
                }
            });


            console.log("Tour deleted successfully"); // !!!

            navigate('/tours'); // !!!  Перенаправляем на страницу туров после удаления
        } catch (error) {
            console.error("Error deleting tour:", error); // !!!
            if (error.response) {

                console.error("Server responded with:", error.response.data); // !!!
            }
        }
    };
    const handleDeleteReview = async (reviewId) => {
        try {
            await axios.delete(`http://localhost:8085/api/reviews/${reviewId}`, {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem("token")}`,
                },
            });

            // Обновляем список отзывов после удаления
            setReviews(reviews.filter((review) => review.id !== reviewId));
        } catch (error) {
            console.error("Error deleting review:", error);
            if (error.response) {
                console.error("Server responded with:", error.response.data);
            }
        }
    };


    if (!loading && tour) {
        const {photoUrl, title, description, price, city, distance, maxPeople} = tour;
        const {totalRating, avgRating} = calculateAvgRating(reviews);

        return (
            <>
                <section>
                    <Container>
                        <Row>
                            <Col lg="8">
                                <div className="tour__content">
                                    <img src={photoUrl} alt=""/>
                                    <div className="tour__info">
                                        <h2>{title}</h2>
                                        <div className="d-flex align-items-center gap-5">
                      <span className="tour__rating d-flex align-items-center gap-1">
                        <i className="ri-star-fill" style={{color: "var(--secondary-color)"}}></i>{" "}
                          {avgRating === 0 ? null : avgRating}
                          {totalRating === 0 ? "Not rated" : <span></span>}
                      </span>
                                            <span>
                        <i className="ri-map-pin-user-fill"></i> {city}
                      </span>
                                        </div>
                                        <div className="tour__extra-details">
                      <span>
                        <i className="ri-money-dollar-circle-fill"></i> ${price} /per person/
                      </span>
                                            <span>
                        <i className="ri-pin-distance-fill"></i> {distance} /in km/
                      </span>
                                            <span>
                        <i className="ri-group-fill"></i> {maxPeople}
                      </span>
                                        </div>
                                        <h5>Description</h5>
                                        <p>{description}</p>
                                    </div>

                                    {isAdmin && (// !!!  Кнопка "Delete Tour" отображается только для admin
                                        <Button color="danger" onClick={handleDeleteTour}>
                                            Delete Tour
                                        </Button>
                                    )}

                                    <div className="tour__reviews mt-4">
                                        <h4>Reviews ({reviews?.length} reviews)</h4>

                                        <Form onSubmit={submitHandler}>
                                            <div className="d-flex align-items-center gap-3 mb-4 rating__group">
                                                {[1, 2, 3, 4, 5].map((rating) => (
                                                    <span
                                                        key={rating}
                                                        onClick={() => setTourRating(rating)}
                                                        onMouseEnter={() => setHoverRating(rating)} // Изменение состояния при наведении
                                                        onMouseLeave={() => setHoverRating(null)} // Убираем подсветку
                                                        style={{
                                                            color:
                                                                rating <= (hoverRating || tourRating)
                                                                    ? "var(--secondary-color)" // Золотой цвет для активной или подсвеченной звезды
                                                                    : "lightgray", // Бледный цвет для остальных
                                                            cursor: "pointer",
                                                        }}
                                                    >
                            {rating} <i className="ri-star-fill"></i>
                          </span>
                                                ))}
                                            </div>
                                            <div className="review__input">
                                                <input type="text" ref={reviewMsgRef}
                                                       placeholder="Write your comment here" required/>
                                                <button className="btn primary__btn text-white" type="submit">
                                                    Submit
                                                </button>
                                            </div>
                                        </Form>

                                        <ListGroup className="user__reviews">
                                            {reviews.map((review) => (
                                                <div className="review__item" key={review.id}>
                                                    <img src={avatar} alt="User avatar"/>
                                                    <div className="w-100">
                                                        <div
                                                            className="d-flex align-items-center justify-content-between">
                                                            <div>
                                                                <h6 className="review__username">{review.user?.username}</h6>
                                                                <p>
                                                                    {new Date(review.createdAt).toLocaleDateString("en-US", {
                                                                        day: "numeric",
                                                                        month: "long",
                                                                        year: "numeric",
                                                                    })}
                                                                </p>
                                                                <h6>{review.comment}</h6>
                                                            </div>
                                                            <span className="d-flex align-items-center">
                                    {review.rating}
                                                                <i className="ri-star-fill"></i>
                                  </span>
                                                        </div>
                                                        {/* Кнопка удаления */}
                                                        {(isAdmin || review.user?.id === Number(localStorage.getItem("userId"))) && (
                                                            <button
                                                                className="btn btn-danger mt-2"
                                                                onClick={() => handleDeleteReview(review.id)}
                                                            >
                                                                Delete
                                                            </button>
                                                        )}
                                                    </div>
                                                </div>
                                            ))}
                                        </ListGroup>


                                    </div>
                                </div>
                            </Col>

                            <Col lg="4">
                                <Booking tour={tour} avgRating={avgRating}/>
                            </Col>
                        </Row>
                    </Container>
                </section>
                <Newsletter/>
            </>
        );
    }

    return <div>Loading...</div>;
};

export default TourDetails;
