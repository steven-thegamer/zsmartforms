using db.entity.header from '../entities/header-entity';

namespace db.entity.document;

entity Document @(Capabilities.MediaType) {
  key header : Association to header.Header @mandatory;
  name : String @mandatory;
  documentData : LargeBinary @stream @Core.MediaType: 'application/pdf' @Core.ContentDisposition.Filename: name @Core.ContentDisposition.Type: 'inline';
}