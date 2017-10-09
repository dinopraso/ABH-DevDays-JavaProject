import Ember from 'ember';

export function eqHelper(params) {
  return params[0] === params[1];
}

export default Ember.Helper.helper(eqHelper);
