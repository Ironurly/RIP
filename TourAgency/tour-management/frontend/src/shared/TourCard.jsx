import React, { useState, useEffect } from "react";
import { Card, CardBody } from "reactstrap";
import { Link } from "react-router-dom";
import axios from "axios"; // Подключаем axios
import calculateAvgRating from "../utils/avgRating";

import "./tour-card.css";

const TourCard = ({ tour }) => {
  const { id, title, city, photoUrl, price, featured } = tour;

  const [reviews, setReviews] = useState([]); // Локальное состояние для отзывов
  const [loading, setLoading] = useState(true); // Для индикатора загрузки
  const [error, setError] = useState(null); // Для обработки ошибок

  // Функция для загрузки отзывов
  useEffect(() => {
    const fetchReviews = async () => {
      try {
        const response = await axios.get(`/api/tours/${id}/reviews`); // Запрос к backend
        setReviews(response.data); // Устанавливаем полученные отзывы
        setLoading(false); // Загрузка завершена
      } catch (err) {
        console.error("Error fetching reviews:", err);
        setError("Failed to load reviews");
        setLoading(false); // Загрузка завершена с ошибкой
      }
    };

    fetchReviews(); // Вызываем загрузку
  }, [id]); // Запрос будет выполняться при изменении `id`

  // Расчёт рейтинга на основе отзывов
  const { totalRating, avgRating } = calculateAvgRating(reviews);

  return (
      <div className="tour__card">
        <Card>
          <div className="tour__img">
            <img src={photoUrl} alt="tour-img" />
            {featured && <span>Featured</span>}
          </div>
          <CardBody>
            <div className="card__top d-flex align-items-center justify-content-between">
            <span className="tour__location d-flex align-items-center gap-1">
              <i className="ri-map-pin-line"></i> {city}
            </span>
              <span className="tour__rating d-flex align-items-center gap-1">
              {loading ? (
                  <i>Loading...</i>
              ) : error ? (
                  <i className="error">{error}</i>
              ) : (
                  <>
                    <i className="ri-star-fill"></i>
                    {reviews.length === 0 ? "Not rated" : avgRating}

                  </>
              )}
            </span>
            </div>

            <h5 className="tour__title">
              <Link to={`/tours/${id}`}>{title}</Link>
            </h5>
            <div className="card__bottom d-flex align-items-center justify-content-between mt-3">
              <h5>
                ${price} <span>/per person/</span>
              </h5>

              <button className="btn booking__btn">
                <Link to={`/tours/${id}`}>Book Now</Link>
              </button>
            </div>
          </CardBody>
        </Card>
      </div>
  );
};

export default TourCard;
