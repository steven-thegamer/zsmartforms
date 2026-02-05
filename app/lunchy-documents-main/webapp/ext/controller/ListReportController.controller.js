sap.ui.define([
	'sap/ui/core/mvc/ControllerExtension',
	'sap/m/PDFViewer',
	'sap/m/MessageToast'
], function (ControllerExtension, PDFViewer, MessageToast) {
	'use strict';

	return ControllerExtension.extend('lunchydocumentsmain.ext.controller.ListReportController', {
		// this section allows to extend lifecycle hooks or hooks provided by Fiori elements
		override: {
			/**
             * Called when a controller is instantiated and its View controls (if available) are already created.
             * Can be used to modify the View before it is displayed, to bind event handlers and do other one-time initialization.
             * @memberOf lunchydocumentsmain.ext.controller.ListReportController
             */
			onInit: function () {
				// you can access the Fiori elements extensionAPI via this.base.getExtensionAPI
				var oModel = this.base.getExtensionAPI().getModel();
				this._pdfViewer = new PDFViewer({
					isTrustedSource : true
				});
				this.getView().addDependent(this._pdfViewer);

			}
		},
        /**
         * Generated event handler.
         *
         * @param oContext the context of the page on which the event was fired. `undefined` for list report page.
         * @param aSelectedContexts the selected contexts of the table rows.
         */
        previewDocument: async function(oContext, aSelectedContexts) {
			const odataModel = this.getView()?.getModel();
			const selectedHeader = aSelectedContexts[0];
			const objectifiedHeader = selectedHeader.getObject();
			const sServiceUrl = odataModel.getServiceUrl();
            
			const actionPath = "MainService.generateDocument(...)";
			const actionBinding = odataModel.bindContext(actionPath, selectedHeader);
			await actionBinding.invoke();
            this._pdfViewer.setSource(`${sServiceUrl}lunchyDocumentDocuments('${objectifiedHeader.documentNumber}')/documentData`);
			this._pdfViewer.setTitle(`Lunchy Document - ${objectifiedHeader.documentNumber}`);
            this._pdfViewer.open();
            
        }
	});
});
