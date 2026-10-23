
# Development Notes

## Requirements and gaps

| Area | Current status | Notes |
|---|---|---|
| R1: Room validation | Implemented and tested | Nonexistent rooms are rejected. |
| R2: Future bookings | Implemented and tested | Bookings must start after the injected clock's current time. |
| R3: Maximum duration | Implemented and tested | Three hours is allowed; longer bookings are rejected. |
| R4: Room conflicts | Implemented and tested | Overlapping bookings for the same room are rejected. |
| R5: User conflicts | Implemented and tested | A user cannot make overlapping bookings in different rooms. |
| R6: Cancellation | Implemented and tested | Users can cancel their own bookings; other users' bookings are protected. |
| R7: Chronological listing | Implemented and tested | A user's bookings are returned in ascending start-time order. |
| TDD commit history | Needs review | Some booking rules were implemented together rather than through separate failing-test and implementation commits. Existing history has not been rewritten to conceal this. |
| TDD test-commit target | Needs audit | Check the commit history and verify the number of genuine failing `test:` commits against the assessment requirements. |

## AI assistance and corrections

### Correction 1: Invalid TimeSlot test data

An overlap test was initially suggested with a zero-duration time slot. The TimeSlot validation rejected this because the end time must be after the start time. The test data was corrected to use valid, positive-duration intervals.

**Lesson:** Test data must satisfy the domain object's validation rules before testing the behaviour of a higher-level service.

### Correction 2: Combining multiple booking rules

The booking service implementation was initially provided with several validation rules together. This did not follow the assessment's strict test-first process for each rule.

**Correction:** The workflow was reviewed and additional individual tests were added for validation and booking conflicts. The original commit history was retained rather than rewritten.

**Lesson:** In TDD, each behaviour should be developed through a failing test, a minimal implementation, and a passing test.

### Correction 3: Distinguishing confirmation tests from red tests

Tests for behaviour already implemented were sometimes described as additional coverage. Such tests can be useful, but they are not genuine red-phase tests when they pass immediately.

**Correction:** These commits were identified as `confirm:` coverage rather than being presented as proof of a failing-test cycle.

**Lesson:** A compilation error is not a valid red phase, and a test that passes immediately must not be counted as a genuine failing `test:` commit.

## Design decisions

- `BookingService` receives a `Clock` through its constructor to make time-dependent tests deterministic.
- `IRoomRepository` and `IBookingRepository` separate service logic from storage.
- `MemoryRoomRepository` and `MemoryBookingRepository` provide in-memory implementations.
- `TimeSlot` represents a booking interval and determines whether two intervals overlap.
- Booking lists are sorted by their start time before being returned.
