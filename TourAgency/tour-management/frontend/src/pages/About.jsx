import React from "react";
import "../styles/about.css";
import aboutUs from "./../assets/images/aboutUs.png";
import WhatWeOffer from "./../assets/images/what-we-offer.png";
import OurMission from "./../assets/images/ourmission.png";
import WheChooseUs from "./../assets/images/why-choose-us.png";
import OurValues from "./../assets/images/our-values.png";
import StartToday from "./../assets/images/start-today.png";
import Newsletter from "../shared/Newsletter";
import Footer from "../components/Footer/Footer";

const sections = [
    {
        title: "Who We Are",
        content: (
            <ul>
                We are a passionate team of travel enthusiasts dedicated to making your dream vacations a reality. Our agency was founded with a single mission: to connect travelers with the world's most stunning destinations through seamless, personalized, and exciting travel planning.
            </ul>
        ),
        image: aboutUs,
    },
    {
        title: "What We Offer",
        content: (
            <ul>
                <li><strong>Customized Travel Packages:</strong> Tailor-made experiences to suit your preferences and budget.</li>
                <li><strong>Expert Guides:</strong> Knowledgeable local guides to enrich your journey with stories and insights.</li>
                <li><strong>Seamless Booking:</strong> An intuitive web app that makes planning your trip effortless.</li>
                <li><strong>Diverse Destinations:</strong> From serene beaches to vibrant cities and hidden natural wonders, we have it all.</li>
            </ul>
        ),
        image: WhatWeOffer,
    },
    {
        title: "Our Mission",
        content: (
            <ul>
                To inspire and empower travelers to explore the world with ease and confidence. We believe travel isn’t just about visiting places—it’s about creating memories, discovering cultures, and connecting with the world around us.
            </ul>
        ),
        image: OurMission,
    },
    {
        title: "Why Choose Us?",
        content: (
            <ul>
                <li><strong>User-Friendly Technology:</strong> Our web app simplifies your travel planning with ease and efficiency.</li>
                <li><strong>Trusted Network:</strong> Collaborations with top-rated tour operators and accommodations worldwide.</li>
                <li><strong>Commitment to Quality:</strong> Every journey is meticulously curated for an unforgettable experience.</li>
            </ul>
        ),
        image: WheChooseUs,
    },
    {
        title: "Our Values",
        content: (
            <ul>
                <li><strong>Customer Satisfaction:</strong> Your happiness is our top priority.</li>
                <li><strong>Sustainability:</strong> We support eco-friendly travel practices to preserve the beauty of our planet.</li>
                <li><strong>Diversity:</strong> We celebrate the uniqueness of every destination and culture.</li>
            </ul>
        ),
        image: OurValues,
    },
    {
        title: "Start Today",
        content: (
            <ul>
                <li><strong>Customer Satisfaction:</strong> Your happiness is our top priority.</li>
                <li><strong>Sustainability:</strong> We support eco-friendly travel practices to preserve the beauty of our planet.</li>
                <li><strong>Diversity:</strong> We celebrate the uniqueness of every destination and culture.</li>
            </ul>
        ),
        image: StartToday,
    },
];
const About = () => {
    return (
        <>
            <section>
                <div className="container">
                    {sections.map((section, index) => (
                        <div
                            key={index}
                            className={`section ${index % 2 !== 0 ? "reverse" : ""}`}
                        >
                            <h2 className="title">{section.title}</h2>
                            <div className="section-content">
                                <div className="text">
                                    <div className="content">{section.content}</div>
                                </div>
                                <div className="image">
                                    <img src={section.image} alt={section.title} />
                                </div>
                            </div>
                        </div>
                    ))}
                </div>
            </section>
            <Newsletter />
            <Footer/>
        </>
    );
};

export default About;