import Ember from 'ember';

export default Ember.Route.extend({
	ajax: Ember.inject.service(),
	queryParams: {
	  currentPage: {
      refreshModel: true, 
      },
	},
	model(params) {
		return Ember.RSVP.hash({
			logs: this.get('ajax').request('/admin/getAllActivityLogs?pageSize=19&pageNumber=' + params.currentPage),
	    });
	},
});

