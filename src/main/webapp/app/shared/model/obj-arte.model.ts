import { ICollecPerma } from 'app/shared/model/collec-perma.model';
import { ICollecPresta } from 'app/shared/model/collec-presta.model';
import { IPintura } from 'app/shared/model/pintura.model';
import { IEscultura } from 'app/shared/model/escultura.model';
import { IOtroObj } from 'app/shared/model/otro-obj.model';
import { IArtista } from 'app/shared/model/artista.model';
import { IExhibicion } from 'app/shared/model/exhibicion.model';

export interface IObjArte {
  id?: number;
  idObjArt?: string;
  paisCultura?: string;
  anio?: string | null;
  tituloObj?: string;
  descObj?: string;
  epocaObj?: string;
  collecPermas?: ICollecPerma[] | null;
  collecPrestas?: ICollecPresta[] | null;
  pinturas?: IPintura[] | null;
  esculturas?: IEscultura[] | null;
  otroObjs?: IOtroObj[] | null;
  artistas?: IArtista[] | null;
  exhibicion?: IExhibicion | null;
}

export const defaultValue: Readonly<IObjArte> = {};
