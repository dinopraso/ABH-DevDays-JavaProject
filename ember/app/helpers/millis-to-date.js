import Ember from 'ember';

export function millisToDate(params) {
  let date = new Date(params[0]);
  return date.toDateString() + ' ' + date.toLocaleTimeString();
}

export default Ember.Helper.helper(millisToDate);
