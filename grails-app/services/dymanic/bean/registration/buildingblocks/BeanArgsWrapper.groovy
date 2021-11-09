package dymanic.bean.registration.buildingblocks

class BeanArgsWrapper {

    @Delegate
    final List<Object> args

    private BeanArgsWrapper(Object... args) {
        this.args = Arrays.asList(args)
    }

    static BeanArgsWrapper of(Object... args) {
        return new BeanArgsWrapper(args)
    }

}
