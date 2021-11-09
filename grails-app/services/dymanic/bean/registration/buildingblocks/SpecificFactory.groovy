package dymanic.bean.registration.buildingblocks

/**
 * Factory methods must use the wrapper when the first parameter is a Class.
 * Otherwise it will conflict with ambiguous methods in the Spring Bean Factory
 **/
class SpecificFactory {

    static final String FACTORY_NAME = ''
    static final String METHOD_FACTORY_NAME = ''

    def <T> T getActivity(BeanArgsWrapper args) {
        assert !args.isEmpty()

        Class<T> clazz = args[0] as Class<T>
        return clazz.newInstance()
    }

}
