import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IExhibicion, defaultValue } from 'app/shared/model/exhibicion.model';

export const ACTION_TYPES = {
  FETCH_EXHIBICION_LIST: 'exhibicion/FETCH_EXHIBICION_LIST',
  FETCH_EXHIBICION: 'exhibicion/FETCH_EXHIBICION',
  CREATE_EXHIBICION: 'exhibicion/CREATE_EXHIBICION',
  UPDATE_EXHIBICION: 'exhibicion/UPDATE_EXHIBICION',
  PARTIAL_UPDATE_EXHIBICION: 'exhibicion/PARTIAL_UPDATE_EXHIBICION',
  DELETE_EXHIBICION: 'exhibicion/DELETE_EXHIBICION',
  RESET: 'exhibicion/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IExhibicion>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ExhibicionState = Readonly<typeof initialState>;

// Reducer

export default (state: ExhibicionState = initialState, action): ExhibicionState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EXHIBICION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EXHIBICION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_EXHIBICION):
    case REQUEST(ACTION_TYPES.UPDATE_EXHIBICION):
    case REQUEST(ACTION_TYPES.DELETE_EXHIBICION):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_EXHIBICION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_EXHIBICION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EXHIBICION):
    case FAILURE(ACTION_TYPES.CREATE_EXHIBICION):
    case FAILURE(ACTION_TYPES.UPDATE_EXHIBICION):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_EXHIBICION):
    case FAILURE(ACTION_TYPES.DELETE_EXHIBICION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_EXHIBICION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_EXHIBICION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_EXHIBICION):
    case SUCCESS(ACTION_TYPES.UPDATE_EXHIBICION):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_EXHIBICION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_EXHIBICION):
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

const apiUrl = 'api/exhibicions';

// Actions

export const getEntities: ICrudGetAllAction<IExhibicion> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_EXHIBICION_LIST,
    payload: axios.get<IExhibicion>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IExhibicion> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EXHIBICION,
    payload: axios.get<IExhibicion>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IExhibicion> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EXHIBICION,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IExhibicion> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EXHIBICION,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IExhibicion> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_EXHIBICION,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IExhibicion> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EXHIBICION,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
