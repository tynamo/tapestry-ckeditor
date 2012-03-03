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
 * Counts allocated CKEditor instances and places the number in the innerHTML of
 * the element with the given id.
 */
Tapestry.Initializer.countCKEditorInstances = function(elementId) {

	var ckEditorInstances = 0;

	if (window.CKEDITOR != undefined)
		for (ckeditorId in CKEDITOR.instances)
			++ckEditorInstances;

	$(elementId).innerHTML = ckEditorInstances;
}

Tapestry.Initializer.printCKEditorInstancesInfo = function(elementId) {

	if (window.CKEDITOR == undefined)
		return;

	function dtdd(elementId, name, val) {
		$(elementId).appendChild($(document.createElement('dt')).update(name));
		$(elementId).appendChild($(document.createElement('dd')).update(val));
	}

	for (ckeditorId in CKEDITOR.instances) {
		ckeditor = CKEDITOR.instances[ckeditorId];

		var dl = document.createElement('dl');
		$(elementId).appendChild(dl);

		dtdd(dl, 'id', ckeditorId);
		dtdd(dl, 'data', ckeditor.getData());
	}
}