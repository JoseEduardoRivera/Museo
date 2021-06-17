import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IArtista, defaultValue } from 'app/shared/model/artista.model';

export const ACTION_TYPES = {
  FETCH_ARTISTA_LIST: 'artista/FETCH_ARTISTA_LIST',
  FETCH_ARTISTA: 'artista/FETCH_ARTISTA',
  CREATE_ARTISTA: 'artista/CREATE_ARTISTA',
  UPDATE_ARTISTA: 'artista/UPDATE_ARTISTA',
  PARTIAL_UPDATE_ARTISTA: 'artista/PARTIAL_UPDATE_ARTISTA',
  DELETE_ARTISTA: 'artista/DELETE_ARTISTA',
  RESET: 'artista/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IArtista>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ArtistaState = Readonly<typeof initialState>;

// Reducer

export default (state: ArtistaState = initialState, action): ArtistaState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ARTISTA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ARTISTA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ARTISTA):
    case REQUEST(ACTION_TYPES.UPDATE_ARTISTA):
    case REQUEST(ACTION_TYPES.DELETE_ARTISTA):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_ARTISTA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ARTISTA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ARTISTA):
    case FAILURE(ACTION_TYPES.CREATE_ARTISTA):
    case FAILURE(ACTION_TYPES.UPDATE_ARTISTA):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_ARTISTA):
    case FAILURE(ACTION_TYPES.DELETE_ARTISTA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ARTISTA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_ARTISTA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ARTISTA):
    case SUCCESS(ACTION_TYPES.UPDATE_ARTISTA):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_ARTISTA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ARTISTA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/artistas';

// Actions

export const getEntities: ICrudGetAllAction<IArtista> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ARTISTA_LIST,
    payload: axios.get<IArtista>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IArtista> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ARTISTA,
    payload: axios.get<IArtista>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IArtista> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ARTISTA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IArtista> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ARTISTA,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IArtista> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_ARTISTA,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IArtista> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ARTISTA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
