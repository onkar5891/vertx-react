import React from "react";
import Table from "react-bootstrap/Table";
import MovieModel from "../types/MovieModel";
import Movie from "./Movie";
import { Container } from "react-bootstrap";

interface Props {
  movies: MovieModel[];
}

const MovieList = (props: Props) => {
  return (
    <Container style={{ margin: "1rem" }}>
      <Table striped bordered size="sm">
        <thead style={{ fontWeight: "bold" }}>
          <tr>
            <td align="center">Name</td>
            <td align="center">Genre</td>
          </tr>
        </thead>
        <tbody>
          {props.movies?.length > 0 ? (
            props.movies.map((m) => (
              <Movie key={m.id} name={m.name} genre={m.genre} />
            ))
          ) : (
            <tr>
              <td colSpan={2}>No movies.</td>
            </tr>
          )}
        </tbody>
      </Table>
    </Container>
  );
};

export default MovieList;
