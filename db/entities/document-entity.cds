using db.entity.header from '../entities/header-entity';

namespace db.entity.document;

entity Document @(Capabilities.MediaType) {
  key header : Association to header.Header;
  name : String;
  documentData : LargeBinary @Core.MediaType: 'application/pdf';
}