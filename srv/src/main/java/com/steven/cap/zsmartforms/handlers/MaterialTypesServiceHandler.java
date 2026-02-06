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

import cds.gen.materialtypesservice.MaterialTypes;
import cds.gen.materialtypesservice.MaterialTypesCreateDraftContext;
import cds.gen.materialtypesservice.MaterialTypesService_;
import cds.gen.materialtypesservice.MaterialTypes_;

@Component
@ServiceName(MaterialTypesService_.CDS_NAME)
public class MaterialTypesServiceHandler implements EventHandler {
    
    private final PersistenceService db;
    private final DraftService materialTypesService;

    /**
     * Constructor to inject the PersistenceService for database operations.
     * @param db PersistenceService instance for running queries
     */
    public MaterialTypesServiceHandler(PersistenceService db, @Qualifier(MaterialTypesService_.CDS_NAME) DraftService materialTypesService) {
        this.db = db;
        this.materialTypesService = materialTypesService;
    }

    private final String prefixMaterialTypeNumber = "MT";

    @On(entity = MaterialTypes_.CDS_NAME, event = MaterialTypesCreateDraftContext.CDS_NAME)
    public void createDraft(MaterialTypesCreateDraftContext context) {
        Integer existingCount = db.run(Select.from(MaterialTypes_.CDS_NAME))
        .listOf(MaterialTypes.class).size();
        MaterialTypes draftMaterialType = MaterialTypes.create();
        draftMaterialType.setMaterialType(prefixMaterialTypeNumber + String.format("%02d", existingCount + 1));
        context.setResult(materialTypesService.newDraft(
            Insert.into(MaterialTypes_.CDS_NAME)
            .entry(draftMaterialType))
            .single(MaterialTypes.class));
        context.setCompleted();
    }
}
