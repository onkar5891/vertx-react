import React, { useState } from "react";
import Alert from "react-bootstrap/Alert";
import { Container, Row } from "react-bootstrap";
import MovieForm from "./components/MovieForm";
import MovieList from "./components/MovieList";
import MovieModel from "./types/MovieModel";
import Axios from "axios";

const App = () => {
  const loadMovies = (): MovieModel[] => {
    let loadedMovies: MovieModel[] = [];
    Axios.get("/movies")
      .then((res) => (loadedMovies = res.data))
      .catch(() => alert("Couldn't load movies.."));

    return loadedMovies;
  };

  const [movies, setMovies] = useState<MovieModel[]>(loadMovies());

  const addMovie = (newMovie: MovieModel) => {
    Axios.post("/movies", newMovie)
      .then((res) => setMovies([...movies, res.data]))
      .catch(() => alert("Couldn't save the movie.."));
  };

  return (
    <Container>
      <Row style={{ justifyContent: "center", fontSize: "2rem" }}>
        <Alert>Movies</Alert>
      </Row>
      <Row md={8}>
        <MovieForm onNewMovie={addMovie}></MovieForm>
      </Row>
      <Row md={8}>
        <MovieList movies={movies}></MovieList>
      </Row>
    </Container>
  );
};

export default App;
