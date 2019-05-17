package ms.vm;

public abstract class VMAbstractObservable implements VMObservable {

	private boolean changed;

	public VMAbstractObservable() {
		changed = false;
	}

	synchronized protected void setChanged(boolean changed) {
		this.changed = changed;
	}

	@Override
	public void reset() {
		setChanged(false);
	}

	@Override
	synchronized public boolean hasChanged() {
		return changed;
	}

}
