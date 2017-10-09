import Ember from 'ember';

export function restaurantTableEnum(index) {
  return parseInt(index) + 1;
}

export default Ember.Helper.helper(restaurantTableEnum);
