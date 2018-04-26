# --- !Ups

INSERT INTO restaurant_cuisine(restaurant_id, cuisine_id) SELECT restaurant.id, cuisine.id FROM restaurant, cuisine WHERE restaurant.name = 'Brimstone' AND cuisine.name = 'American';
INSERT INTO restaurant_cuisine(restaurant_id, cuisine_id) SELECT restaurant.id, cuisine.id FROM restaurant, cuisine WHERE restaurant.name = 'Crickets' AND cuisine.name = 'American';
INSERT INTO restaurant_cuisine(restaurant_id, cuisine_id) SELECT restaurant.id, cuisine.id FROM restaurant, cuisine WHERE restaurant.name = 'Friends' AND cuisine.name = 'Chinese';
INSERT INTO restaurant_cuisine(restaurant_id, cuisine_id) SELECT restaurant.id, cuisine.id FROM restaurant, cuisine WHERE restaurant.name = 'Mirage' AND cuisine.name = 'Italian';
INSERT INTO restaurant_cuisine(restaurant_id, cuisine_id) SELECT restaurant.id, cuisine.id FROM restaurant, cuisine WHERE restaurant.name = 'Roadhouse' AND cuisine.name = 'Tibetan';

# --- !Downs
