import React, { useState, useEffect } from "react";
import CommonSection from "../shared/CommonSection";
import "../styles/tour.css";
import TourCard from "./../shared/TourCard";
import SearchBar from "./../shared/SearchBar";
import { Col, Container, Row } from "reactstrap";
import Newsletter from "./../shared/Newsletter";
import axios from "axios";
import Footer from "../components/Footer/Footer";

const Tours = () => {
    const [pageCount, setPageCount] = useState(0);
    const [page, setPage] = useState(0);
    const [tours, setTours] = useState([]); // Туры из БД
    const [loading, setLoading] = useState(true);
    const itemsPerPage = 8; // Количество туров на странице

    useEffect(() => {
        const fetchTours = async () => {
            try {
                const response = await axios.get("http://localhost:8085/api/tours");
                setTours(response.data);
            } catch (error) {
                console.error("Error fetching tours:", error);
            } finally {
                setLoading(false);
            }
        };

        fetchTours();
    }, []);

    useEffect(() => {
        const pages = Math.ceil(tours.length / itemsPerPage);
        setPageCount(pages);
    }, [tours]);

    // Отображаемые туры на текущей странице
    const displayedTours = tours.slice(page * itemsPerPage, (page + 1) * itemsPerPage);

    return (
        <>
            <CommonSection title={"All Tours"} />
            <section>
                <Container>
                    <Row>
                        <SearchBar />
                    </Row>
                </Container>
            </section>
            <section className="pt-0">
                <Container>
                    <Row>
                        {loading ? (
                            <div>Loading tours...</div>
                        ) : (
                            displayedTours.map((tour) => (
                                <Col lg="3" className="mb-4" key={tour.id}>
                                    <TourCard tour={tour} />
                                </Col>
                            ))
                        )}
                        <Col lg="12">
                            <div className="pagination d-flex align-items-center justify-content-center mt-4 gap-3">
                                {Array.from({ length: pageCount }, (_, index) => (
                                    <span
                                        key={index}
                                        onClick={() => setPage(index)}
                                        className={page === index ? "active__page" : ""}
                                    >
                    {index + 1}
                  </span>
                                ))}
                            </div>
                        </Col>
                    </Row>
                </Container>
            </section>
            <Newsletter />
            <Footer />
        </>
    );
};

export default Tours;