CREATE TABLE novel_genre
(
    genre_id INT NOT NULL,
    novel_id INT NOT NULL,
    PRIMARY KEY (genre_id, novel_id)
);
