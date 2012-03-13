// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * An object for keeping track of the form event handlers added by each CKEDITOR
 * instance, needed for proper cleanup of destroyed CKEDITOR instances.
 */
Tapestry.ckeditor = {
	formEventHandlers : {}
};

/**
 * Tapestry initialization for tapestry-ckeditor.
 */
Tapestry.Initializer.initCKEditor = function(textareaId, ckeditorInitJSON) {

	/*
	 * If the textarea with id=ckeditorId cannot be found (probably because if
	 * tapestry zone update), than destroy it's corresponding ckeditor instance.
	 */
	for (ckeditorId in CKEDITOR.instances)
		if ($(ckeditorId) == undefined)
			// destroy the ckeditor instance without updating the textarea
			CKEDITOR.instances[ckeditorId].destroy(true);

	// init CKEditor for the given textarea
	CKEDITOR.replace(textareaId, ckeditorInitJSON);

	var updateTextArea = function() {
		/*
		 * if the ckeditor instance with id=textareaId cannot be found, than it
		 * has been destroyed so remove the corresponding eventHandler listening
		 * on FORM_PREPARE_FOR_SUBMIT_EVENT,
		 * 
		 * else update the textarea with the ckeditor contentnts before the form
		 * is submitted so that the corresponding server side property is
		 * updated.
		 */
		var ckeditorInstance = CKEDITOR.instances[textareaId];
		if (ckeditorInstance == undefined)
			document.stopObserving(Tapestry.FORM_PREPARE_FOR_SUBMIT_EVENT,
					Tapestry.ckeditor.formEventHandlers[textareaId]);
		else
			ckeditorInstance.updateElement(); // update the textarea
	};

	Tapestry.ckeditor.formEventHandlers[textareaId] = updateTextArea;
	document.observe(Tapestry.FORM_PREPARE_FOR_SUBMIT_EVENT, updateTextArea);
};