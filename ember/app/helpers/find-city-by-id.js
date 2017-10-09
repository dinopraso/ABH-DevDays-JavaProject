import Ember from 'ember';

export function findCityById(params) {
  return params[0].findBy('city.id', params[1]).city.name;
}

export default Ember.Helper.helper(findCityById);
