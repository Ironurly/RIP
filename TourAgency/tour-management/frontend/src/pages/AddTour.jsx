import React, { useState } from 'react';
import { Container, Row, Col, Form, FormGroup, Label, Input, Button } from 'reactstrap';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const AddTour = () => {
    const navigate = useNavigate();
    const [newTour, setNewTour] = useState({
        title: '',
        description: '',
        city: '',
        price: '',
        photoUrl: '',
        featured: false,


    });

    const handleChange = (e) => {
        setNewTour({ ...newTour, [e.target.name]: e.target.value });
    };

    const handleFeaturedChange = (e) => {
        setNewTour({ ...newTour, featured: e.target.checked });
    }


    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await axios.post('http://localhost:8085/api/tours', newTour,
                {
                    headers: {
                        Authorization: `Bearer ${localStorage.getItem("token")}`
                    }
                }
            );
            navigate('/'); // Перенаправление после успешного добавления
        } catch (error) {
            console.error("Error adding tour:", error);
            if(error.response){
                console.error("Server responded with:", error.response.data);
            }

        }
    };

    return (
        <Container>
            <Row>
                <Col lg="6" className="m-auto">
                    <h2>Add New Tour</h2>
                    <Form onSubmit={handleSubmit}>
                        <FormGroup>
                            <Label for="title">Title</Label>
                            <Input type="text" name="title" id="title" onChange={handleChange} required />
                        </FormGroup>
                        <FormGroup>
                            <Label for="description">Description</Label>
                            <Input type="textarea" name="description" id="description" onChange={handleChange} required />
                        </FormGroup>
                        <FormGroup>
                            <Label for="city">City</Label>
                            <Input type="text" name="city" id="city" onChange={handleChange} required />
                        </FormGroup>
                        <FormGroup>
                            <Label for="price">Price</Label>
                            <Input type="number" name="price" id="price" onChange={handleChange} required />
                        </FormGroup>

                        <FormGroup>
                            <Label for="photoUrl">Photo URL</Label>
                            <Input type="text" name="photoUrl" id="photoUrl" onChange={handleChange} required />
                        </FormGroup>
                        <FormGroup>
                            <Label for="maxPeople">Max people</Label>
                            <Input type="text" name="maxPeople" id="maxPeople" onChange={handleChange} required />
                        </FormGroup>
                        <FormGroup>
                            <Label for="distance">Distance</Label>
                            <Input type="text" name="distance" id="distance" onChange={handleChange} required />
                        </FormGroup>
                        <FormGroup check>
                            <Input type="checkbox" name="featured" id="featured" onChange={handleFeaturedChange} />
                            <Label check for="featured">
                                Featured
                            </Label>
                        </FormGroup>

                        <Button type="submit" color="primary">Save</Button>
                    </Form>
                </Col>
            </Row>
        </Container>
    );
};

export default AddTour;