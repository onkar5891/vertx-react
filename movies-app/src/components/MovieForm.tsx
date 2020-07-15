import React, { FormEvent, useState } from "react";
import { Form, Button, Col, Container, Row } from "react-bootstrap";
import MovieModel from "../types/MovieModel";

interface Props {
  onNewMovie: (movie: MovieModel) => void;
}

const MovieForm = (props: Props) => {
  const genres = ["Action", "Comedy", "Thriller", "Crime"];
  const [name, setName] = useState("");
  const [genre, setGenre] = useState(genres[0]);

  const handleSubmit = (event: FormEvent) => {
    event.preventDefault();
    props.onNewMovie({
      name: name,
      genre: genre,
    });
  };

  return (
    <Container style={{ margin: "1rem" }}>
      <Row style={{ justifyContent: "center" }}>
        <Form onSubmit={handleSubmit}>
          <Form.Row>
            <Col>
              <Form.Control
                placeholder="Name"
                required
                value={name}
                onChange={(event) => setName(event.target.value)}
              />
            </Col>
            <Col>
              <Form.Control
                as="select"
                required
                value={genre}
                onChange={(event) => setGenre(event.target.value)}
              >
                {genres.map((g) => (
                  <option key={g}>{g}</option>
                ))}
              </Form.Control>
            </Col>
            <Col>
              <Button variant="primary" type="submit">
                Submit
              </Button>
            </Col>
          </Form.Row>
        </Form>
      </Row>
    </Container>
  );
};

export default MovieForm;
