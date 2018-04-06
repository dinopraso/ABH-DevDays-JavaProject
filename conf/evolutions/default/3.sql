# --- !Ups

INSERT INTO restaurant_review(restaurant_id, user_id, rating, review) SELECT restaurant.id, "user".id, 5, 'Lorem ipsum' FROM restaurant, "user" WHERE restaurant.name = 'Brimstone' AND "user".name = 'Regular User';
INSERT INTO restaurant_review(restaurant_id, user_id, rating, review) SELECT restaurant.id, "user".id, 5, 'Lorem ipsum' FROM restaurant, "user" WHERE restaurant.name = 'Brimstone' AND "user".name = 'Regular User';
INSERT INTO restaurant_review(restaurant_id, user_id, rating, review) SELECT restaurant.id, "user".id, 5, 'Lorem ipsum' FROM restaurant, "user" WHERE restaurant.name = 'Brimstone' AND "user".name = 'Regular User';
INSERT INTO restaurant_review(restaurant_id, user_id, rating, review) SELECT restaurant.id, "user".id, 5, 'Lorem ipsum' FROM restaurant, "user" WHERE restaurant.name = 'Brimstone' AND "user".name = 'Regular User';

INSERT INTO restaurant_cuisine(restaurant_id, cuisine_id) SELECT restaurant.id, cuisine.id FROM restaurant, cuisine WHERE restaurant.name = 'Crickets' AND cuisine.name = 'Mexican';
INSERT INTO restaurant_cuisine(restaurant_id, cuisine_id) SELECT restaurant.id, cuisine.id FROM restaurant, cuisine WHERE restaurant.name = 'Crickets' AND cuisine.name = 'Korean';
INSERT INTO restaurant_cuisine(restaurant_id, cuisine_id) SELECT restaurant.id, cuisine.id FROM restaurant, cuisine WHERE restaurant.name = 'Friends' AND cuisine.name = 'Mexican';
INSERT INTO restaurant_cuisine(restaurant_id, cuisine_id) SELECT restaurant.id, cuisine.id FROM restaurant, cuisine WHERE restaurant.name = 'Little Persia' AND cuisine.name = 'American';
INSERT INTO restaurant_cuisine(restaurant_id, cuisine_id) SELECT restaurant.id, cuisine.id FROM restaurant, cuisine WHERE restaurant.name = 'Little Persia' AND cuisine.name = 'Continental';
INSERT INTO restaurant_cuisine(restaurant_id, cuisine_id) SELECT restaurant.id, cuisine.id FROM restaurant, cuisine WHERE restaurant.name = 'Melting Pot' AND cuisine.name = 'Continental';

# --- !Downs

TRUNCATE restaurant_review;
TRUNCATE restauran_cuisine;