import Ember from 'ember';

const {
  inject: {
    service,
  },
} = Ember;

export default Ember.Controller.extend({
  ajax: service('ajax'),

  actions: {
    editUser() {
      this.get('ajax').patch('/admin/editUser', {
        xhrFields: {
          withCredentials: true,
        },
        contentType: 'application/json',
        data: JSON.stringify(this.get('model.user')),
      })
      .then(() => this.transitionToRoute('admin.users'));
    },
  },
});
