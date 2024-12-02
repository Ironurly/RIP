import React, {useState, useEffect} from 'react';
import "../styles/home.css";
import {Container, Row, Col} from "reactstrap";
import heroImg from "../assets/images/hero-img01.jpg";
import heroImg02 from "../assets/images/hero-img02.jpg";
import worldImg from "../assets/images/world.png";
import heroVideo from "../assets/images/hero-video.mp4";
import experienceImg from "../assets/images/experience.png";
import Subtitle from "./../shared/Subtitle";

import SearchBar from "../shared/SearchBar";
import ServiceList from "../services/ServiceList";

import MasonryImagesGallery from "../components/image-gallery/MasonryImagesGallery";
import Testimonial from "../components/Testimonial/Testimonial";
import Newsletter from "../shared/Newsletter";
import TourCard from "./../shared/TourCard";
import axios from 'axios';
import Footer from "../components/Footer/Footer"; // !!! Импортируйте axios

const Home = () => {
    const [tours, setTours] = useState([]); // !!! State для хранения туров из БД
    const [featuredTours, setFeaturedTours] = useState([]) // !!!
    const [loading, setLoading] = useState(true); // !!!  State для индикации загрузки

    useEffect(() => {
        const fetchTours = async () => {
            try {
                const response = await axios.get('http://localhost:8085/api/tours'); // !!! Запрос на backend
                setTours(response.data);
                const featured = response.data.filter(tour => tour.featured === true); // !!! Фильтрация featured туров
                setFeaturedTours(featured);


            } catch (error) {
                console.error("Error fetching tours:", error);
            } finally {

                setLoading(false);

            }
        };
        fetchTours();
    }, []);

    return (
        <>
            {/*================= hero section start =============*/}
            <section>
                <Container>
                    <Row>
                        <Col lg="6">
                            <div className="hero__content">
                                <div className="hero__subtitle d-flex align-items-center">
                                    <Subtitle subtitle={"Know Before You Go"}/>
                                    <img src={worldImg} alt=""/>
                                </div>
                                <h1>
                                    Traveling opens door to creating{" "}
                                    <span className="highlight"> memories</span>
                                </h1>
                                <p>
                                    Lorem ipsum dolor sit amet, consectetur adipisicing elit.
                                    Repudiandae corporis numquam sequi minima omnis nihil quaerat
                                    consequuntur, eaque saepe doloribus expedita minus incidunt
                                    fuga facilis consequatur nulla officiis quo rerum!
                                </p>
                            </div>
                        </Col>

                        <Col lg="2">
                            <div className="hero__img-box">
                                <img src={heroImg} alt=""/>
                            </div>
                        </Col>
                        <Col lg="2">
                            <div className="hero__img-box mt-4">
                                <video src={heroVideo} alt="" controls/>
                            </div>
                        </Col>
                        <Col lg="2">
                            <div className="hero__img-box mt-5">
                                <img src={heroImg02} alt=""/>
                            </div>
                        </Col>

                        <SearchBar/>
                    </Row>
                </Container>
            </section>
            {/*================= hero section start =============*/}
            <section>
                <Container>
                    <Row>
                        <Col lg="3">
                            <h5 className="services__subtitle">What we server</h5>
                            <h2 className="services__title">We offer our best services</h2>
                        </Col>
                        <ServiceList/>
                    </Row>
                </Container>
            </section>


            {/*============= featured tour section start ===============*/}
            <section>
                <Container>
                    <Row>
                        <Col lg="12" className="mb-5">
                            <Subtitle subtitle={"Explore"}/>
                            <h2 className="featured__tour-title">Our featured tours</h2>
                        </Col>

                        {/* !!! Условный рендеринг */}
                        {loading ? ( // !!!  Если loading === true, отображаем индикатор загрузки
                            <div>Loading tours...</div> // !!!  Индикатор загрузки
                        ) : (
                            featuredTours.map((tour) => ( // !!!  Используем tours из state
                                <Col lg="3" className="mb-4" key={tour.id}>
                                    <TourCard tour={tour}/>
                                </Col>
                            ))
                        )}


                    </Row>
                </Container>
            </section>
            {/*============= featured tour section end ===============*/}

            {/*=================experience section start======================*/}
            <section>
                <Container>
                    <Row>
                        <Col lg="6">
                            <div className="experience__content">
                                <Subtitle subtitle={"Experience"}/>
                                <h2>
                                    With our all experience <br/> we will serve you
                                </h2>
                                <p>
                                    Lorem, ipsum dolor sit amet consectetur adipisicing elit.{" "}
                                    <br/>
                                    Commodi modi at quos in expedita dicta temporibus voluptas
                                    sunt natus! Qui, adipisci quasi et consectetur alias
                                    asperiores? Libero beatae voluptate enim?
                                </p>
                            </div>

                            <div className="counter__wrapper d-flex align-items-center gap-5">
                                <div className="counter__box">
                                    <span>2k+</span>
                                    <h6>Regular clients</h6>
                                </div>
                                <div className="counter__box">
                                    <span>12k+</span>
                                    <h6>Successful Trip</h6>
                                </div>
                                <div className="counter__box">
                                    <span>6</span>
                                    <h6>Years of experience</h6>
                                </div>
                            </div>
                        </Col>
                        <Col lg="6">
                            <div className="experience__img">
                                <img src={experienceImg} alt=""/>
                            </div>
                        </Col>
                    </Row>
                </Container>
            </section>
            {/*=================experience section end ======================= */}

            {/*================= gallery section start ======================= */}
            <section>
                <Container>
                    <Row>
                        <Col lg="12">
                            <Subtitle subtitle={"Gallery"}/>
                            <h2 className="gallery__title">
                                Visit our customers tour gallery
                            </h2>
                        </Col>
                        <Col lg="12">
                            <MasonryImagesGallery/>
                        </Col>
                    </Row>
                </Container>
            </section>
            {/*================= gallery section end   ======================= */}
            {/*================= testimonial section start =================== */}
            <section>
                <Container>
                    <Row>
                        <Col lg="12">
                            <Subtitle subtitle={"Fans Love"}/>
                            <h2 className="testimonial__title">
                                What our clients say about us
                            </h2>
                        </Col>
                        <Col lg="12">
                            <Testimonial/>
                        </Col>
                    </Row>
                </Container>
            </section>
            {/*================= testimonial section end =================== */}
            <Newsletter/>
            <Footer/>
        </>
    );
};

export default Home;
