package com.steven.cap.zsmartforms.handlers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.sap.cds.ql.Insert;
import com.sap.cds.ql.Select;
import com.sap.cds.services.draft.DraftService;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.On;
import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cds.services.persistence.PersistenceService;

import cds.gen.db.entity.materialtype.MaterialType;
import cds.gen.db.entity.materialtype.MaterialType_;
import cds.gen.materialtypeservice.LunchyDocumentMaterialTypesCreateDraftContext;
import cds.gen.materialtypeservice.LunchyDocumentMaterialTypes_;
import cds.gen.materialtypeservice.MaterialTypeService_;

@Component
@ServiceName(MaterialType_.CDS_NAME)
public class MaterialTypeCreateDraftHandler implements EventHandler {

    @Qualifier(MaterialTypeService_.CDS_NAME)
    private PersistenceService db;
    private DraftService materialtypeService;

    // Inject the PersistenceService to run queries
    public MaterialTypeCreateDraftHandler(PersistenceService db, 
        @Qualifier(MaterialTypeService_.CDS_NAME) DraftService materialtypeService) {
        this.db = db;
        this.materialtypeService = materialtypeService;
    }

    private final String initialCode = "C";

    @On(entity = LunchyDocumentMaterialTypes_.CDS_NAME, event = LunchyDocumentMaterialTypesCreateDraftContext.CDS_NAME)
    MaterialType onCreateDraftMaterialType(LunchyDocumentMaterialTypesCreateDraftContext context) {
        MaterialType newMaterialType = MaterialType.create();
        Integer totalDocument = db.run(
            Select.from(MaterialType_.CDS_NAME)
        ).listOf(MaterialType.class).size();
        String newDocumentNumber = String.format("%s%06d", initialCode, totalDocument + 1);
        newMaterialType.setLanguageCode("EN");
        newMaterialType.setMaterialType(newDocumentNumber);
        return materialtypeService.newDraft(Insert.into(LunchyDocumentMaterialTypes_.CDS_NAME).entry(newMaterialType)).single(MaterialType.class);
    }

}
