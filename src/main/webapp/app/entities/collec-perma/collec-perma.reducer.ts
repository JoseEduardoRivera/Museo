import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICollecPerma, defaultValue } from 'app/shared/model/collec-perma.model';

export const ACTION_TYPES = {
  FETCH_COLLECPERMA_LIST: 'collecPerma/FETCH_COLLECPERMA_LIST',
  FETCH_COLLECPERMA: 'collecPerma/FETCH_COLLECPERMA',
  CREATE_COLLECPERMA: 'collecPerma/CREATE_COLLECPERMA',
  UPDATE_COLLECPERMA: 'collecPerma/UPDATE_COLLECPERMA',
  PARTIAL_UPDATE_COLLECPERMA: 'collecPerma/PARTIAL_UPDATE_COLLECPERMA',
  DELETE_COLLECPERMA: 'collecPerma/DELETE_COLLECPERMA',
  RESET: 'collecPerma/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICollecPerma>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type CollecPermaState = Readonly<typeof initialState>;

// Reducer

export default (state: CollecPermaState = initialState, action): CollecPermaState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_COLLECPERMA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_COLLECPERMA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_COLLECPERMA):
    case REQUEST(ACTION_TYPES.UPDATE_COLLECPERMA):
    case REQUEST(ACTION_TYPES.DELETE_COLLECPERMA):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_COLLECPERMA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_COLLECPERMA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_COLLECPERMA):
    case FAILURE(ACTION_TYPES.CREATE_COLLECPERMA):
    case FAILURE(ACTION_TYPES.UPDATE_COLLECPERMA):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_COLLECPERMA):
    case FAILURE(ACTION_TYPES.DELETE_COLLECPERMA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_COLLECPERMA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_COLLECPERMA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_COLLECPERMA):
    case SUCCESS(ACTION_TYPES.UPDATE_COLLECPERMA):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_COLLECPERMA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_COLLECPERMA):
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

const apiUrl = 'api/collec-permas';

// Actions

export const getEntities: ICrudGetAllAction<ICollecPerma> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_COLLECPERMA_LIST,
    payload: axios.get<ICollecPerma>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ICollecPerma> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_COLLECPERMA,
    payload: axios.get<ICollecPerma>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICollecPerma> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_COLLECPERMA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICollecPerma> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_COLLECPERMA,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ICollecPerma> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_COLLECPERMA,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICollecPerma> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_COLLECPERMA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
