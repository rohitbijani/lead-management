import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './interest.reducer';

export const InterestDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const interestEntity = useAppSelector(state => state.interest.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="interestDetailsHeading">
          <Translate contentKey="leadManagementApp.interest.detail.title">Interest</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{interestEntity.id}</dd>
          <dt>
            <span id="category">
              <Translate contentKey="leadManagementApp.interest.category">Category</Translate>
            </span>
          </dt>
          <dd>{interestEntity.category}</dd>
          <dt>
            <span id="brand">
              <Translate contentKey="leadManagementApp.interest.brand">Brand</Translate>
            </span>
          </dt>
          <dd>{interestEntity.brand}</dd>
          <dt>
            <span id="articleId">
              <Translate contentKey="leadManagementApp.interest.articleId">Article Id</Translate>
            </span>
          </dt>
          <dd>{interestEntity.articleId}</dd>
          <dt>
            <span id="orderId">
              <Translate contentKey="leadManagementApp.interest.orderId">Order Id</Translate>
            </span>
          </dt>
          <dd>{interestEntity.orderId}</dd>
          <dt>
            <span id="isPurchased">
              <Translate contentKey="leadManagementApp.interest.isPurchased">Is Purchased</Translate>
            </span>
          </dt>
          <dd>{interestEntity.isPurchased ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="leadManagementApp.interest.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>{interestEntity.createdAt ? <TextFormat value={interestEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="leadManagementApp.interest.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{interestEntity.createdBy}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="leadManagementApp.interest.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>{interestEntity.updatedAt ? <TextFormat value={interestEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="leadManagementApp.interest.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{interestEntity.updatedBy}</dd>
          <dt>
            <Translate contentKey="leadManagementApp.interest.lead">Lead</Translate>
          </dt>
          <dd>{interestEntity.lead ? interestEntity.lead.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/interest" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/interest/${interestEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default InterestDetail;
