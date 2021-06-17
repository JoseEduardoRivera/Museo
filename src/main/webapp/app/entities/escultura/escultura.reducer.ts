import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEscultura, defaultValue } from 'app/shared/model/escultura.model';

export const ACTION_TYPES = {
  FETCH_ESCULTURA_LIST: 'escultura/FETCH_ESCULTURA_LIST',
  FETCH_ESCULTURA: 'escultura/FETCH_ESCULTURA',
  CREATE_ESCULTURA: 'escultura/CREATE_ESCULTURA',
  UPDATE_ESCULTURA: 'escultura/UPDATE_ESCULTURA',
  PARTIAL_UPDATE_ESCULTURA: 'escultura/PARTIAL_UPDATE_ESCULTURA',
  DELETE_ESCULTURA: 'escultura/DELETE_ESCULTURA',
  RESET: 'escultura/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEscultura>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type EsculturaState = Readonly<typeof initialState>;

// Reducer

export default (state: EsculturaState = initialState, action): EsculturaState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ESCULTURA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ESCULTURA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ESCULTURA):
    case REQUEST(ACTION_TYPES.UPDATE_ESCULTURA):
    case REQUEST(ACTION_TYPES.DELETE_ESCULTURA):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_ESCULTURA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ESCULTURA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ESCULTURA):
    case FAILURE(ACTION_TYPES.CREATE_ESCULTURA):
    case FAILURE(ACTION_TYPES.UPDATE_ESCULTURA):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_ESCULTURA):
    case FAILURE(ACTION_TYPES.DELETE_ESCULTURA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ESCULTURA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_ESCULTURA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ESCULTURA):
    case SUCCESS(ACTION_TYPES.UPDATE_ESCULTURA):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_ESCULTURA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ESCULTURA):
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

const apiUrl = 'api/esculturas';

// Actions

export const getEntities: ICrudGetAllAction<IEscultura> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ESCULTURA_LIST,
    payload: axios.get<IEscultura>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IEscultura> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ESCULTURA,
    payload: axios.get<IEscultura>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IEscultura> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ESCULTURA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEscultura> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ESCULTURA,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IEscultura> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_ESCULTURA,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEscultura> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ESCULTURA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
