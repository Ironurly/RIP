// SearchResultList.jsx
import React, {useState, useEffect} from 'react';
import {useLocation} from 'react-router-dom';
import {Container, Row, Col} from 'reactstrap';
import TourCard from '../shared/TourCard';
import axios from 'axios';

const SearchResultList = () => {
    const location = useLocation();
    const [tours, setTours] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const searchParams = new URLSearchParams(location.search);
        const city = searchParams.get('city');
        const distance = searchParams.get('distance');
        const maxGroupSize = searchParams.get('maxGroupSize');

        const fetchTours = async () => {
            try {
                const response = await axios.get('http://localhost:8085/api/tours/search', { // !!!  Запрос на backend с параметрами
                    params: { // !!! Передаем параметры запроса
                        city,
                        distance,
                        maxGroupSize
                    }
                });
                setTours(response.data);
            } catch (error) {
                console.error("Error fetching tours:", error);
            } finally {
                setLoading(false);
            }
        };

        fetchTours();
    }, [location.search]);

    if (loading) {
        return <div>Loading tours...</div>; // или другой индикатор загрузки
    }

    // Фильтрация туров должна происходить на backend

    return (
        <Container>
            <Row>
                {tours.map((tour) => (
                    <Col lg="3" md="6" sm="6" key={tour.id}> {/* key is important */}
                        <TourCard tour={tour}/>
                    </Col>
                ))}
            </Row>
        </Container>
    );
};

export default SearchResultList;