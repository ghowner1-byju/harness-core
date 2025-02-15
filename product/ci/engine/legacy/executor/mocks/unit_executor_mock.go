// Copyright 2021 Harness Inc. All rights reserved.
// Use of this source code is governed by the PolyForm Free Trial 1.0.0 license
// that can be found in the licenses directory at the root of this repository, also available at
// https://polyformproject.org/wp-content/uploads/2020/05/PolyForm-Free-Trial-1.0.0.txt.

// Code generated by MockGen. DO NOT EDIT.
// Source: unit_executor.go

// Package executor is a generated GoMock package.
package executor

import (
	context "context"
	gomock "github.com/golang/mock/gomock"
	output "github.com/harness/harness-core/product/ci/engine/output"
	proto "github.com/harness/harness-core/product/ci/engine/proto"
	reflect "reflect"
)

// MockUnitExecutor is a mock of UnitExecutor interface.
type MockUnitExecutor struct {
	ctrl     *gomock.Controller
	recorder *MockUnitExecutorMockRecorder
}

// MockUnitExecutorMockRecorder is the mock recorder for MockUnitExecutor.
type MockUnitExecutorMockRecorder struct {
	mock *MockUnitExecutor
}

// NewMockUnitExecutor creates a new mock instance.
func NewMockUnitExecutor(ctrl *gomock.Controller) *MockUnitExecutor {
	mock := &MockUnitExecutor{ctrl: ctrl}
	mock.recorder = &MockUnitExecutorMockRecorder{mock}
	return mock
}

// EXPECT returns an object that allows the caller to indicate expected use.
func (m *MockUnitExecutor) EXPECT() *MockUnitExecutorMockRecorder {
	return m.recorder
}

// Run mocks base method.
func (m *MockUnitExecutor) Run(ctx context.Context, step *proto.UnitStep, so output.StageOutput, accountID string) (*output.StepOutput, error) {
	m.ctrl.T.Helper()
	ret := m.ctrl.Call(m, "Run", ctx, step, so, accountID)
	ret0, _ := ret[0].(*output.StepOutput)
	ret1, _ := ret[1].(error)
	return ret0, ret1
}

// Run indicates an expected call of Run.
func (mr *MockUnitExecutorMockRecorder) Run(ctx, step, so, accountID interface{}) *gomock.Call {
	mr.mock.ctrl.T.Helper()
	return mr.mock.ctrl.RecordCallWithMethodType(mr.mock, "Run", reflect.TypeOf((*MockUnitExecutor)(nil).Run), ctx, step, so, accountID)
}

// Cleanup mocks base method.
func (m *MockUnitExecutor) Cleanup(ctx context.Context, step *proto.UnitStep) error {
	m.ctrl.T.Helper()
	ret := m.ctrl.Call(m, "Cleanup", ctx, step)
	ret0, _ := ret[0].(error)
	return ret0
}

// Cleanup indicates an expected call of Cleanup.
func (mr *MockUnitExecutorMockRecorder) Cleanup(ctx, step interface{}) *gomock.Call {
	mr.mock.ctrl.T.Helper()
	return mr.mock.ctrl.RecordCallWithMethodType(mr.mock, "Cleanup", reflect.TypeOf((*MockUnitExecutor)(nil).Cleanup), ctx, step)
}
