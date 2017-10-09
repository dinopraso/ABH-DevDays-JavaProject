import Ember from 'ember';

const {
  computed,
  String: {
    htmlSafe,
  },
} = Ember;

export default Ember.Component.extend({
  tileStyle: computed('data.profileImagePath', function () {
    return htmlSafe('background-image: url(' + this.get('data.profileImagePath') + '), url(\'/assets/images/rPlaceholder.png\')');
  }),
});
